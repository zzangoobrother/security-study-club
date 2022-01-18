package com.example.securitystudyclub.security.service;

import com.example.securitystudyclub.entity.ClubMember;
import com.example.securitystudyclub.repository.ClubMemberRepository;
import com.example.securitystudyclub.security.dto.ClubAuthMemberDTO;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClubUserDetailsService implements UserDetailsService {

  private final ClubMemberRepository clubMemberRepository;

  public ClubUserDetailsService(ClubMemberRepository clubMemberRepository) {
    this.clubMemberRepository = clubMemberRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("ClubUserDetailsService loadUserByUsername " + username);

    ClubMember clubMember = clubMemberRepository.findByEmail(false, username).orElseThrow(
        () -> new UsernameNotFoundException("Check Email or Social")
    );

    log.info("----------------------------------");
    log.info(clubMember);

    ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
        clubMember.getEmail(),
        clubMember.getPassword(),
        clubMember.isFromSocial(),
        clubMember.getRoleSet().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toSet())
    );

    clubAuthMemberDTO.setName(clubMember.getName());
    clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());

    return clubAuthMemberDTO;
  }
}
