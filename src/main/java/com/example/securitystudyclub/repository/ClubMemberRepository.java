package com.example.securitystudyclub.repository;

import com.example.securitystudyclub.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

}
