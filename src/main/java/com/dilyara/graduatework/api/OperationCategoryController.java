package com.dilyara.graduatework.api;

import com.dilyara.graduatework.entity.OperationCategory;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.service.OperationCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operation-category")
@RequiredArgsConstructor
public class OperationCategoryController {
  private final OperationCategoryService operationCategoryService;

  @GetMapping
  public ResponseEntity<List<OperationCategory>> findAll (
    @AuthenticationPrincipal User auth
  ) {
    return ResponseEntity.ok(operationCategoryService.findAll(auth));
  }
}
