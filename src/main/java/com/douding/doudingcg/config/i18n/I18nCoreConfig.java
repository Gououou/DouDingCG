package com.douding.doudingcg.config.i18n;

import cn.hutool.extra.spring.SpringUtil;
import com.douding.doudingcg.validator.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * @author Guo
 * @create 2024-01-24 10:44
 */
@Component
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class I18nCoreConfig implements WebMvcConfigurer {
    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @Primary
    public LocaleResolver localeResolver() {
        return new I18nConfig();
    }

    @Bean("customMessageSource")
    public ResourceBundleMessageSource resourceBundleMessageSource(){
        return ValidatorUtils.getNewMessageSource();
    }

    /**
     *  国际化使用自定义头
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        I18nHeaderConfig localeInterceptor = new I18nHeaderConfig();
        localeInterceptor.setParamName(Locale.CHINA.getLanguage());
        registry.addInterceptor(localeInterceptor);
    }
}
