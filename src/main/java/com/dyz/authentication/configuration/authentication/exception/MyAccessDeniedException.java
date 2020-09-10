package com.dyz.authentication.configuration.authentication.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 权限认证异常
 */
public class MyAccessDeniedException extends AccessDeniedException {
    public MyAccessDeniedException(String msg) {
        super(msg);
    }
}
