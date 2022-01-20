package com.example.securitystudyclub.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

  private AntPathMatcher antPathMatcher;
  private String pattern;

  public ApiCheckFilter(String pattern) {
    this.antPathMatcher = new AntPathMatcher();
    this.pattern = pattern;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("REQUESTURI : " + request.getRequestURL());
    log.info(antPathMatcher.match(pattern, request.getRequestURI()));

    if (antPathMatcher.match(pattern, request.getRequestURI())) {
      log.info("ApiCheckFilter..........................................");
      log.info("ApiCheckFilter..........................................");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
