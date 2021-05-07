package com.dilyara.graduatework.repo;

import com.dilyara.graduatework.entity.Note;
import com.dilyara.graduatework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
  List<Note> findAllByUser (User user);
}
