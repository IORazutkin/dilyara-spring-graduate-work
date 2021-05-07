package com.dilyara.graduatework.service;

import com.dilyara.graduatework.entity.Note;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.exception.ForbiddenException;
import com.dilyara.graduatework.exception.NotFoundException;
import com.dilyara.graduatework.repo.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
  private final NoteRepo noteRepo;

  public Note findById (User user, Long id) {
    Note note = findById(id);

    if (note.getUser().equals(user)) {
      return note;
    }

    throw new ForbiddenException();
  }

  public Note findById (Long id) {
    return noteRepo.findById(id).orElseThrow(NotFoundException::new);
  }

  public List<Note> findAll (User user) {
    return noteRepo.findAllByUser(user);
  }

  public Note create (User user, Note note) {
    note.setUser(user);
    return noteRepo.save(note);
  }

  public Note update (User user, Long id, Note note) {
    Note noteFromDb = findById(user, id);
    BeanUtils.copyProperties(note, noteFromDb, "id");
    return create(user, noteFromDb);
  }

  public Boolean delete (User user, Long id) {
    Note note = findById(user, id);
    noteRepo.delete(note);
    return true;
  }
}
