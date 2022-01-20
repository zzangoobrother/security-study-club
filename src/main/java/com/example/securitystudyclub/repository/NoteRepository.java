package com.example.securitystudyclub.repository;

import com.example.securitystudyclub.entity.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<Note, Long> {

  @EntityGraph(attributePaths = "writer", type = EntityGraphType.LOAD)
  @Query("select n from Note n where n.num = :num")
  Optional<Note> getWithWriter(@Param("num") Long num);

  @EntityGraph(attributePaths = {"writer"}, type = EntityGraphType.LOAD)
  @Query("select n from Note n where n.writer.email = :email")
  List<Note> getList(@Param("email") String email);
}
