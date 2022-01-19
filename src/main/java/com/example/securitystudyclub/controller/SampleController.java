package com.example.securitystudyclub.controller;

import com.example.securitystudyclub.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

  @PreAuthorize("permitAll()")
  @GetMapping("/all")
  public void exAll() {
    log.info("exAll..................");
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/member")
  public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
    log.info("exMember..................");

    log.info("-------------------------------");
    log.info(clubAuthMemberDTO);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin")
  public void exAdmin() {
    log.info("exAdmin..................");
  }

  @PreAuthorize("#clubAuthMemberDTO != null && #clubAuthMemberDTO.username eq \"user95@test.org\"")
  @GetMapping("/exOnly")
  public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
    log.info("exMemberOnly........................");
    log.info(clubAuthMemberDTO);

    return "/sample/admin";
  }
}
