package com.douding.doudingcg.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Guo
 * @create 2024-05-13 9:58
 * 签名拦截器
 */
@Component
@Slf4j
public class SignInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        //TODO:这里暂未做逻辑操作
        return HandlerInterceptor.super.preHandle(request, response, obj);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String bodyTmp = MultiReadHttpServletRequestWrapper.getBodyTmp();
        log.info("清除 bodyTmp  :{}", bodyTmp);
        MultiReadHttpServletRequestWrapper.clearBodyTmp();
        String rm = MultiReadHttpServletRequestWrapper.getBodyTmp();
        log.info("清除 状态  :{}", StrUtil.isEmpty(rm));
    }
}
