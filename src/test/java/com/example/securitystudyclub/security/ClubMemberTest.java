package com.example.securitystudyclub.security;

import com.example.securitystudyclub.entity.ClubMember;
import com.example.securitystudyclub.entity.ClubMemberRole;
import com.example.securitystudyclub.repository.ClubMemberRepository;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ClubMemberTest {

  @Autowired
  private ClubMemberRepository clubMemberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void insertDummies() {
    // 1 ~ 80까지는 USER
    // 81 ~ 90까지는 USER, MANAGER
    // 91 ~ 100까지는 USER, MANAGER, ADMIN

    IntStream.rangeClosed(1, 100).forEach(i -> {
      ClubMember clubMember = ClubMember.builder()
          .email("user" + i + "@test.org")
          .name("사용자" + i)
          .fromSocial(false)
          .password(passwordEncoder.encode("1111"))
          .build();

      clubMember.addMemberRole(ClubMemberRole.USER);

      if (i > 80) {
        clubMember.addMemberRole(ClubMemberRole.MANAGER);
      }

      if (i > 90) {
        clubMember.addMemberRole(ClubMemberRole.ADMIN);
      }

      clubMemberRepository.save(clubMember);
    });
  }
}
