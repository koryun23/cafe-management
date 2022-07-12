package com.cafe.handler;

import javax.servlet.http.HttpServletRequest;

public interface BasicAuthorizationHttpServletRequestHandler {
    UsernameAndPasswordHolder getUsernameAndPassword(HttpServletRequest request);
}
