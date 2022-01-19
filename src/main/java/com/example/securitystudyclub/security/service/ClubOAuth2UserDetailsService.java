package com.example.securitystudyclub.security.service;

import com.example.securitystudyclub.entity.ClubMember;
import com.example.securitystudyclub.entity.ClubMemberRole;
import com.example.securitystudyclub.repository.ClubMemberRepository;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

  private final ClubMemberRepository clubMemberRepository;
  private final PasswordEncoder passwordEncoder;

  public ClubOAuth2UserDetailsService(ClubMemberRepository clubMemberRepository, PasswordEncoder passwordEncoder) {
    this.clubMemberRepository = clubMemberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("---------------------------------");
    log.info("userRequest : " + userRequest);

    String clientName = userRequest.getClientRegistration().getClientName();
    log.info("clientName : " + clientName);
    log.info(userRequest.getAdditionalParameters());

    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("==================================");
    oAuth2User.getAttributes().forEach((k, v) -> {
      log.info(k + " : " + v);
    });

    String email = null;
    if (clientName.equals("Google")) {
      email = oAuth2User.getAttribute("email");
    }
    log.info("EMAIL : " + email);
    ClubMember clubMember = saveSocialMember(email);

    return oAuth2User;
  }

  private ClubMember saveSocialMember(String email) {
    Optional<ClubMember> result = clubMemberRepository.findByEmail(true, email);
    if (result.isPresent()) {
      return result.get();
    }

    ClubMember clubMember = ClubMember.builder()
        .email(email)
        .name(email)
        .password(passwordEncoder.encode("1111"))
        .fromSocial(true)
        .build();

    clubMember.addMemberRole(ClubMemberRole.USER);

    return clubMemberRepository.save(clubMember);
  }
}
