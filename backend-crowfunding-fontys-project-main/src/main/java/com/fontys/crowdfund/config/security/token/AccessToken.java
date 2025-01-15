package com.fontys.crowdfund.config.security.token;

import java.util.Collection;

public interface AccessToken {

    Long getUserId();

    Collection<String> getRole();

    boolean hasRole(String roleName);
}
