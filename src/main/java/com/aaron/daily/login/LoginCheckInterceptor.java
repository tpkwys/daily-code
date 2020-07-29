package com.aaron.daily.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: daily-code
 * @description: 登录检验拦截器
 * @author: tianpanke
 * @create: 2020-07-29
 **/
@Component
@Slf4j
public class LoginCheckInterceptor implements PathPatternInterceptor{

    @Autowired
    List<LoginContextProvider> loginContextProviderList;

    @Override
    public String getPathPattern() {
        return "/**";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;


            LoginRequired loginRequired = method.getBean().getClass().getAnnotation(LoginRequired.class);
            if (loginRequired != null && loginRequired.value()) {
                try {
                    loginContextProviderList.stream()
                            // 过滤出支持该request的provider
                            .filter(provider -> provider.support(request))
                            // 取出第一个
                            .findFirst()
                            // 从request获取loginContext
                            .map(provider -> provider.get(request, response))
                            // 将loginContext绑定至request上下文
                            .ifPresent(LoginContext::bind);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    writeErrorMsg(response, 1, "no_login");
                    return false;
                }

                // 如果当前上下文找不到loginContext则返回异常
                if (LoginContext.current() == null) {
                    writeErrorMsg(response, 1, "no_login");
                    return false;
                }
            }
        }
        return false;
    }

    private void writeErrorMsg(HttpServletResponse response,int code,String message){
        ResponeUtils.writeObject(message,response);
    }
}
