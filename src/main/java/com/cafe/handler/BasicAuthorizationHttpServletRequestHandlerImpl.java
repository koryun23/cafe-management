package com.cafe.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
public class BasicAuthorizationHttpServletRequestHandlerImpl implements BasicAuthorizationHttpServletRequestHandler {

    @Override
    public UsernameAndPasswordHolder getUsernameAndPassword(HttpServletRequest request) {
        String encodedCredentials = getEncodedCredentialsFromRequest(request);
        String decodedCredentials = getDecodedCredentialsFromEncodedCredentials(encodedCredentials);
        int separatorIndex = decodedCredentials.indexOf(":");
        return new UsernameAndPasswordHolder(
                getUsernameFromDecodedCredentials(decodedCredentials, separatorIndex),
                getPasswordFromDecodedCredentials(decodedCredentials, separatorIndex)
        );
    }

    private String getEncodedCredentialsFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(6);
    }

    private String getDecodedCredentialsFromEncodedCredentials(String encodedCredentials) {
        return new String(Base64.getDecoder().decode(encodedCredentials));
    }

    private String getUsernameFromDecodedCredentials(String decodedCredentials, int separatorIndex) {
        return decodedCredentials.substring(0, separatorIndex);
    }

    private String getPasswordFromDecodedCredentials(String decodedCredentials, int separatorIndex) {
        return decodedCredentials.substring(separatorIndex + 1);
    }
}
