package com.dyz.authentication.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderUtil extends BCryptPasswordEncoder {

    /**
     * 密码加密
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    /**
     * 验证密码   rawPassword 明码 ， encodedPassword数据库中的加密后的密码
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return super.matches(rawPassword,encodedPassword);
    }

}
