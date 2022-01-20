package com.example.securitystudyclub.config;

import com.example.securitystudyclub.security.filter.ApiCheckFilter;
import com.example.securitystudyclub.security.filter.ApiLoginFailHandler;
import com.example.securitystudyclub.security.filter.ApiLoginFilter;
import com.example.securitystudyclub.security.handler.ClubLoginSuccessHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//        .withUser("user1")
//        .password("$2a$10$b/kN5EVr2jyn.Ju1T.gXKui.SNFoh0fU1DCgaAgVWAZMxuFfAX3qe")
//        .roles("USER");
//  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//        .antMatchers("/sample/all").permitAll()
//        .antMatchers("/sample/member").hasRole("USER");

    http.formLogin();
    http.csrf().disable();
    http.logout();

    http.oauth2Login().successHandler(successHandler());
    http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService());

    http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public ClubLoginSuccessHandler successHandler() {
    return new ClubLoginSuccessHandler(passwordEncoder());
  }

  @Bean
  public ApiCheckFilter apiCheckFilter() {
    return new ApiCheckFilter("/notes/**/*");
  }

  @Bean
  public ApiLoginFilter apiLoginFilter() throws Exception {
    ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
    apiLoginFilter.setAuthenticationManager(authenticationManager());
    apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
    return apiLoginFilter;
  }
}
