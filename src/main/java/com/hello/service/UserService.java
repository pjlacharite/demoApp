package com.hello.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

public class UserService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken openIDAuthenticationToken) throws UsernameNotFoundException {
        return new User(openIDAuthenticationToken.getName(), "", AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
