package com.example.securitystudyclub.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

  public ApiLoginFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    log.info("-----------------------ApiLoginFilter-------------------------------");
    log.info("attemptAuthentication");

    String email = request.getParameter("email");
    String pw = request.getParameter("pw");

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);

    return getAuthenticationManager().authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    log.info("-----------------------successfulAuthentication-------------------------------");
    log.info("successfulAuthentication : " + authResult);
    log.info(authResult.getPrincipal());
  }
}
