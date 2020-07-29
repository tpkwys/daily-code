package com.aaron.daily.login;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @program: daily-code
 * @description: 登录上下文
 * @author: tianpanke
 * @create: 2020-07-29
 **/
@Data
@Builder
public class LoginContext {

    private static final String LOGIN_CONTEXT="login_context";

    private Long userId;

    private String nickName;

    private String realName;

    @Tolerate
    public LoginContext(){}

    //绑定到当前上下文
    public void bind(){
        setValInRequest(LOGIN_CONTEXT,this);
    }

    //获取登录上下文
    public static LoginContext current(){
        return getValInRequest(LOGIN_CONTEXT);
    }

    //从request中获取name对应的属性
    private static <T> T getValInRequest(String name){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Object val = requestAttributes.getAttribute(name,ServletRequestAttributes.SCOPE_REQUEST);
        return val == null?null:(T)val;
    }

    //将name-val设置到request属性钟
    private static <T> void setValInRequest(String name,T val){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        requestAttributes.setAttribute(name,val,ServletRequestAttributes.SCOPE_REQUEST);
    }
}
