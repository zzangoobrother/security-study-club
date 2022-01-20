package com.example.securitystudyclub.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
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

      boolean checkHeader = checkAuthHeader(request);
      if (checkHeader) {
        filterChain.doFilter(request, response);
      } else {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        String message = "FAIL CHECK API TOKEN";
        json.put("code", "403");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);
      }

      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean checkAuthHeader(HttpServletRequest request) {
    boolean checkResult = false;
    String authHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authHeader)) {
      log.info("Authorization exist : " + authHeader);
      if (authHeader.equals("12345678")) {
        checkResult = true;
      }
    }

    return checkResult;
  }
}