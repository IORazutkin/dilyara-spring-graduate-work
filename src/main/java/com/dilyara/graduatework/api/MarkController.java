package com.dilyara.graduatework.api;

import com.dilyara.graduatework.entity.Mark;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mark")
@RequiredArgsConstructor
public class MarkController {
  private final MarkService markService;

  @GetMapping
  public ResponseEntity<List<Mark>> findAll (
    @AuthenticationPrincipal User auth,
    @RequestParam String index
  ) {
    return ResponseEntity.ok(markService.findAll(auth, index));
  }

  @PostMapping
  public ResponseEntity<Mark> createNew (
    @AuthenticationPrincipal User auth,
    @RequestBody Mark mark
  ) {
    return ResponseEntity.ok(markService.create(auth, mark));
  }

  @PutMapping("{id}")
  public ResponseEntity<Mark> update (
    @AuthenticationPrincipal User auth,
    @PathVariable Long id,
    @RequestBody Mark mark
  ) {
    return ResponseEntity.ok(markService.update(auth, id, mark));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> delete (
    @AuthenticationPrincipal User auth,
    @PathVariable Long id
  ) {
    return ResponseEntity.ok(markService.delete(auth, id));
  }
}
