package com.example.securitystudyclub.service;

import com.example.securitystudyclub.dto.NoteDTO;
import com.example.securitystudyclub.entity.Note;
import com.example.securitystudyclub.repository.NoteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;

  public NoteServiceImpl(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  public Long register(NoteDTO noteDTO) {
    Note note = dtoToEntity(noteDTO);
    log.info("===========================");
    log.info(note);

    Note saveNote = noteRepository.save(note);
    return saveNote.getNum();
  }

  @Override
  public NoteDTO get(Long num) {
    Optional<Note> result = noteRepository.getWithWriter(num);
    if (result.isPresent()) {
      return entityToDTO(result.get());
    }

    return null;
  }

  @Override
  public void modify(NoteDTO noteDTO) {
    Long num = noteDTO.getNum();

    Optional<Note> result = noteRepository.findById(num);
    if (result.isPresent()) {
      Note note = result.get();
      note.changeTitle(noteDTO.getTitle());
      note.changeContent(noteDTO.getContent());
      noteRepository.save(note);
    }
  }

  @Override
  public void remove(Long num) {
    noteRepository.deleteById(num);
  }

  @Override
  public List<NoteDTO> getAllWithWriter(String writerEmail) {
    List<Note> noteList = noteRepository.getList(writerEmail);
    return noteList.stream().map(this::entityToDTO).collect(Collectors.toList());
  }
}
