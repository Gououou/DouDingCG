package com.douding.doudingcg.validator;

import cn.hutool.extra.spring.SpringUtil;
import com.douding.doudingcg.config.i18n.I18nConfig;
import com.douding.doudingcg.exception.PramsException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

/**
 * @author Guo
 * @create 2024-04-26 17:21
 */
@Slf4j
public class ValidatorUtils {

    public static ResourceBundleMessageSource getNewMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setBasenames("i18n-com/validation", "i18n/validation");
        return bundleMessageSource;
    }

    private static ResourceBundleMessageSource getMessageSource() {
        try {
            return SpringUtil.getBean("customMessageSource", ResourceBundleMessageSource.class);
        }catch (NoSuchBeanDefinitionException e) {
            return ValidatorUtils.getNewMessageSource();
        }
    }

    public static String message(String msg, Object ... args){
        if(StringUtils.isEmpty(msg)){
            return msg;
        }
        ResourceBundleMessageSource messageSource = getMessageSource();
        I18nConfig i18nConfig = new I18nConfig();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Locale defaultLocale = i18nConfig.resolveLocale(((ServletRequestAttributes) requestAttributes).getRequest());
        if (defaultLocale == null) {
            defaultLocale = Locale.CHINA;
        }
        messageSource.setDefaultLocale(defaultLocale);
        try {
            return messageSource.getMessage(msg.trim(), args, defaultLocale).trim();
        }catch (Exception e) {
            return msg;
        }
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws PramsException {
        log.info("校验对象前:{}  --> {}", Locale.getDefault(), LocaleContextHolder.getLocale());
        Locale.setDefault(LocaleContextHolder.getLocale());
        log.info("校验对象后:{}  --> {}", Locale.getDefault(), LocaleContextHolder.getLocale());
        Validator validator = Validation.byDefaultProvider().configure().messageInterpolator(
                        new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(getMessageSource())))
                .buildValidatorFactory().getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new PramsException(constraint.getMessage());
        }
    }

    /**
     * 校验对象，包含基础对象（不含group）、指定组对象
     * @param object        待校验对象
     * @param groups        待校验的组
     */
    public static void validateEntities(Object object, Class<?>... groups)
            throws PramsException {
        Locale.setDefault(LocaleContextHolder.getLocale());
        log.info("校验对象，包含基础对象（不含group）、指定组对象后:{}  --> {}", Locale.getDefault(), LocaleContextHolder.getLocale());
        Validator validator = Validation.byDefaultProvider().configure().messageInterpolator(
                        new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(getMessageSource())))
                .buildValidatorFactory().getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        Set<ConstraintViolation<Object>> constraintViolationsByGroup = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            ConstraintViolation<Object> next = constraintViolationsByGroup.iterator().next();
            throw new PramsException(constraint.getMessage()+next.getMessage());
        }
    }
}
