package com.example.securitystudyclub.repository;

import com.example.securitystudyclub.entity.ClubMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

  @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraphType.LOAD)
  @Query("select m from ClubMember m where m.fromSocial = :social and m.email = :email")
  Optional<ClubMember> findByEmail(@Param("email") String email, @Param("social") boolean social);
}
