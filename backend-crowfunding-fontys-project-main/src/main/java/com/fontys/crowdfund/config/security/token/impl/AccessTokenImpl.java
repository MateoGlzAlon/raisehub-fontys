package com.fontys.crowdfund.config.security.token.impl;

import com.fontys.crowdfund.config.security.token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final Long userId;
    private final Set<String> roles;

    public AccessTokenImpl(Long userId, Collection<String> roles) {
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @Override
    public Collection<String> getRole() {
        return this.roles;
    }

    @Override
    public boolean hasRole(String roleName) {
        return this.roles.contains(roleName);
    }
}
