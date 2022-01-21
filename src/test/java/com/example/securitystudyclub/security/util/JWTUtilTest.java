package com.example.securitystudyclub.security.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JWTUtilTest {

  private JWTUtil jwtUtil;

  @BeforeEach
  void testInit() {
    System.out.println("testBefore....................");
    jwtUtil = new JWTUtil();
  }

  @Test
  void testEncode() throws Exception {
    String email = "user95@test.org";
    String str = jwtUtil.generateToken(email);
    System.out.println(str);
  }

  @Test
  void testValidate() throws Exception {
    String email = "user95@test.org";
    String str = jwtUtil.generateToken(email);

    Thread.sleep(5000);

    String resultEmail = jwtUtil.validateAndExtract(str);
    System.out.println(resultEmail);
  }
}