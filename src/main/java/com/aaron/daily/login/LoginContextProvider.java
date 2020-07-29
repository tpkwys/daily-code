package com.aaron.daily.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

public interface LoginContextProvider {

    //从request中获取LoginContext
    LoginContext get(HttpServletRequest request, HttpServletResponse response);

    //是否支持该请求
    boolean support(HttpServletRequest request);

    //获取sessionId Optional
    default Optional<String> getCookieValueOps(String cookieName,HttpServletRequest request){
        return Optional.ofNullable(request.getCookies())
                .map(Stream::of)
                .orElse(Stream.empty())
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
