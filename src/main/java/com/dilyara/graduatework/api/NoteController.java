package com.dilyara.graduatework.api;

import com.dilyara.graduatework.entity.Note;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
  private final NoteService noteService;

  @GetMapping
  public ResponseEntity<List<Note>> findAll (
    @AuthenticationPrincipal User auth
  ) {
    return ResponseEntity.ok(noteService.findAll(auth));
  }

  @PostMapping
  public ResponseEntity<Note> createNew (
    @AuthenticationPrincipal User auth,
    @RequestBody Note note
  ) {
    return ResponseEntity.ok(noteService.create(auth, note));
  }

  @PutMapping("{id}")
  public ResponseEntity<Note> update (
    @AuthenticationPrincipal User auth,
    @PathVariable Long id,
    @RequestBody Note note
  ) {
    return ResponseEntity.ok(noteService.update(auth, id, note));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> delete (
    @AuthenticationPrincipal User auth,
    @PathVariable Long id
  ) {
    return ResponseEntity.ok(noteService.delete(auth, id));
  }
}
