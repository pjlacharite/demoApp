package com.demoapp.configuration;

import com.demoapp.service.UserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.time.Duration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions().sameOrigin()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "default-src 'self'"))
                .addHeaderWriter(new StaticHeadersWriter("X-WebKit-CSP", "default-src 'self'"))
                .httpStrictTransportSecurity()
                .includeSubDomains(true)
                .maxAgeInSeconds(Duration.ofDays(365).getSeconds());
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/img/*", "/", "/home", "/h2-console", "/subscription/**", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .openidLogin()
                    .loginPage("/login")
                    .permitAll()
                    .authenticationUserDetailsService(new UserDetailService())
                    .attributeExchange(".*appdirect.com.*")
                        .attribute("email")
                            .type("http://axschema.org/contact/email")
                            .required(true)
                            .and()
                        .attribute("firstname")
                            .type("http://axschema.org/namePerson/first")
                            .required(true)
                            .and()
                        .attribute("lastname")
                            .type("http://axschema.org/namePerson/last")
                            .required(true);
    }

}