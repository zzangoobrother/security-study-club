package com.example.securitystudyclub.security.handler;

import com.example.securitystudyclub.security.dto.ClubAuthMemberDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  private PasswordEncoder passwordEncoder;

  public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("--------------------------------------");
    log.info("onAuthenticationSuccess");

    ClubAuthMemberDTO clubAuthMemberDTO = (ClubAuthMemberDTO) authentication.getPrincipal();
    boolean fromSocial = clubAuthMemberDTO.isFromSocial();
    log.info("Need Modify Member ? " + fromSocial);

    boolean passwordResult = passwordEncoder.matches("1111", clubAuthMemberDTO.getPassword());

    if (fromSocial && passwordResult) {
      redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
    }
  }
}
