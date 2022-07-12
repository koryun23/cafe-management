package com.cafe.service.core.jwt;

import java.util.Date;

public interface JwtService {

    String createToken(String username, Date expirationDate);

    String getUsername(String token);
}
