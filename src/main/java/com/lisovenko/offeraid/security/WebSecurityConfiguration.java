package com.lisovenko.offeraid.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                c ->
                        c.mvcMatchers(
                                HttpMethod.GET,
                                "/offers",
                                "/offers/create",
                                "/offers/{offerId}/edit",
                                "/offers/favourites")
                                .authenticated())
                .authorizeRequests(
                        c ->
                                c.mvcMatchers(
                                        HttpMethod.POST,
                                        "/offers",
                                        "/{areaUrl}/categories/{categoryUrl}/offers/{offerId}/favourites",
                                        "/{areaUrl}/categories/{categoryUrl}/offers/{offerId}/contact")
                                        .authenticated())
                .authorizeRequests(
                        c ->
                                c.mvcMatchers(
                                        HttpMethod.DELETE,
                                        "/offers/{offerId}",
                                        "/offers/{offerId}/favourites",
                                        "/{areaUrl}/categories/{categoryUrl}/offers/{offerId}/favourites")
                                        .authenticated())
                .authorizeRequests(c -> c.mvcMatchers(HttpMethod.PUT, "/offers").authenticated())
                .authorizeRequests(c -> c.anyRequest().permitAll())
                .formLogin(c -> c.loginPage("/login").permitAll().usernameParameter("email"))
                .logout(c -> c.logoutSuccessUrl("/"));
    }
}

