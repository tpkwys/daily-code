package com.aaron.daily.login;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
    boolean value() default true;
}
