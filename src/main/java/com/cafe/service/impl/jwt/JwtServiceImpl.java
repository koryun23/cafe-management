package com.cafe.service.impl.jwt;

import com.cafe.service.core.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtServiceImpl implements JwtService {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    public JwtServiceImpl(JwtBuilder jwtBuilder, JwtParser jwtParser) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
    }

    @Override
    public String createToken(String username, Date expirationDate) {
        return jwtBuilder
                .setExpiration(expirationDate)
                .claim("tokenId", UUID.randomUUID().toString())
                .claim("username", username).compact();
    }

    @Override
    public String getUsername(String token) {
        Claims body = (Claims) jwtParser.parse(token).getBody();
        return (String) body.get("username");
    }
}
