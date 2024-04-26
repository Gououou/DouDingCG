package com.douding.doudingcg.config.i18n;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Guo
 * @create 2024-01-23 17:46
 */
@Configuration
public class I18nConfig extends AcceptHeaderLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 获取请求中的语言参数来链接
        String i18n = request.getHeader("i18n");
        Locale defaultLocale = this.getDefaultLocale();
        if(Strings.isNotEmpty(i18n)){
            String[] in = i18n.split("_");
            if(in.length == 2){
                defaultLocale = new Locale(in[0], in[1]);
            }else{
                defaultLocale = new Locale(in[0]);
            }
        }

        if (defaultLocale != null) {
            return defaultLocale;
        } else {
            return super.resolveLocale(request);
        }
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        this.setDefaultLocale(locale);
    }
}
