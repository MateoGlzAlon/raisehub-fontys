package com.fontys.crowdfund.config.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
