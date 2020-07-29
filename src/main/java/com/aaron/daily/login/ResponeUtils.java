package com.aaron.daily.login;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @program: daily-code
 * @description:
 * @author: tianpanke
 * @create: 2020-07-29
 **/
@Slf4j
public abstract class ResponeUtils {
    /**
     * 将对象obj当成json写出到response
     */
    public static void writeObject(Object obj, HttpServletResponse response) {
        String resultString = JSONObject.toJSONString(obj);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            out.write(resultString.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
