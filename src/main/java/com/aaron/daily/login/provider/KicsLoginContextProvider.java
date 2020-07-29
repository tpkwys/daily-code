package com.aaron.daily.login.provider;

import com.aaron.daily.login.BizConstant;
import com.kunchi.dtc.rc.api.GetUserApi;
import com.kunchi.dtc.rc.dto.SessionDto;
import com.kunchi.dtc.rc.po.UserItem;
import com.kunchi.isb.base.result.ISBResult;
import com.kunchi.kzw.constant.BizConstant;
import com.kunchi.kzw.exception.KZWException;
import com.kunchi.kzw.login.LoginContext;
import com.kunchi.kzw.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @description:    kics专用loginContext 提供者，从kics session中获取login context
 * @author:         **gexiaobing**
 * @create_time:    2019/12/9 10:00 上午
 */
@Component
public class KicsLoginContextProvider extends DefaultLoginContextProvider {

    @Autowired
    GetUserApi getUserApi;

    @Override
    public LoginContext get(HttpServletRequest request, HttpServletResponse response) {

        Optional<String> kicsLoginNameOps = getCookieValueOps(BizConstant.KICS_LOGIN_NAME,request);
        Optional<String> kicsSessionIdOps = getCookieValueOps(BizConstant.KICS_SESSION_ID,request);
        if(kicsLoginNameOps.isPresent() && kicsSessionIdOps.isPresent()){

            //根据sessionId从统一认证中心获取userItem(用户信息)
            SessionDto logInNameDto = new SessionDto();
            logInNameDto.setLoginName(kicsLoginNameOps.get());
            ISBResult<UserItem> userItemISBResult = getUserApi.process(logInNameDto);
            UserItem userItem = userItemISBResult.getBody();
            if(userItem != null){

                //利用userItem(用户信息) 进行登录
                UserPO userPO = userService.loginFromKics(kicsSessionIdOps.get(), userItem);

                //添加登录信息到cookie
                Cookie cookie = new Cookie(BizConstant.KZW_SESSION_ID,kicsSessionIdOps.get());
                cookie.setHttpOnly(true);
                cookie.setMaxAge(BizConstant.KZW_SESSION_TIMEOUT_IN_KICS);
                cookie.setPath("/");
                response.addCookie(cookie);

                //返回loginContext
                return LoginContext.builder()
                        .userId(userPO.getId())
                        .nickName(userPO.getNickName())
                        .realName(userPO.getRealName())
                        .build();
            }
        }

        // 不存在该用户
        throw KZWException.NO_THIS_USER.get();
    }

    @Override
    public boolean support(HttpServletRequest request) {
        return !super.support(request) && getCookieValueOps(BizConstant.KICS_SESSION_ID,request).isPresent() && getCookieValueOps(BizConstant.KICS_LOGIN_NAME,request).isPresent();
    }
}
