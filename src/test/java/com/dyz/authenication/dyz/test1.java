package com.dyz.authenication.dyz;


import com.dyz.authentication.AuthenticationApplication;
import com.dyz.authentication.util.BCryptPasswordEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = AuthenticationApplication.class)
@Slf4j
public class test1 {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;


    @Test
    void GenratePwd() {
        String encode = bCryptPasswordEncoderUtil.encode("111111");
        log.info(encode);
        String s = UUID.randomUUID().toString();
        log.info(s);
    }
}
