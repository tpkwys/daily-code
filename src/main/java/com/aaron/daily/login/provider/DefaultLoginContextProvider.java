package com.aaron.daily.login.provider;

import com.aaron.daily.login.BizConstant;
import com.aaron.daily.login.LoginContext;
import com.aaron.daily.login.LoginContextProvider;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @program: daily-code
 * @description: 默认loginContext 提供者
 * @author: tianpanke
 * @create: 2020-07-29
 **/
@Component
public class DefaultLoginContextProvider  implements LoginContextProvider {

    @Override
    public LoginContext get(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> sessionIdOps = getCookieValueOps(BizConstant.KZW_SESSION_ID,request);
        if(sessionIdOps.isPresent()){
            UserPO userPO = userService.getUserBySessionId(sessionIdOps.get());
            if(userPO != null){
                return LoginContext.builder()
                        .userId(userPO.getId())
                        .nickName(userPO.getNickName())
                        .realName(userPO.getRealName())
                        .build();
            }
        }
        return null;
    }

    @Override
    public boolean support(HttpServletRequest request) {
        boolean hasLoginName = getCookieValueOps(BizConstant.KICS_LOGIN_NAME,request).isPresent();
        Optional<String> kzwSessionIdOps = getCookieValueOps(BizConstant.KZW_SESSION_ID, request);
        Optional<String> kicsSessionIdOps = getCookieValueOps(BizConstant.KICS_SESSION_ID, request);
        // 判断kzwSessionId和kicsSessionId的值是否一样，说明：一样的话说明是同一个会话
        if(hasLoginName && kzwSessionIdOps.isPresent() && kicsSessionIdOps.isPresent()){
            return kzwSessionIdOps.get().equals(kicsSessionIdOps.get());
        }
        return false;
    }
}
