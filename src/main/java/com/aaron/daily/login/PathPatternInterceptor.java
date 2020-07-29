package com.aaron.daily.login;

import org.springframework.web.servlet.HandlerInterceptor;

public interface PathPatternInterceptor extends HandlerInterceptor {

    /**
     *  获取拦截的url
     * @return
     */
    String getPathPattern();
}
