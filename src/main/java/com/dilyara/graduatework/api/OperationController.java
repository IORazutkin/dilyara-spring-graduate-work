package com.dilyara.graduatework.api;

import com.dilyara.graduatework.dto.operation_dashboard.OperationDashboardDto;
import com.dilyara.graduatework.entity.Operation;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.service.DashboardService;
import com.dilyara.graduatework.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operation")
@RequiredArgsConstructor
public class OperationController {
  private final OperationService operationService;
  private final DashboardService dashboardService;

  @GetMapping
  public ResponseEntity<List<Operation>> findAll (
    @AuthenticationPrincipal User auth,
    @RequestParam(defaultValue = "0") String type,
    @RequestParam String index
  ) {
    return ResponseEntity.ok(operationService.findAll(auth, type, index));
  }

  @GetMapping("/dashboard")
  public ResponseEntity<OperationDashboardDto> findDashboard (
    @AuthenticationPrincipal User auth,
    @RequestParam(defaultValue = "7") String limit,
    @RequestParam(defaultValue = "0") String index
  ) {
    return ResponseEntity.ok(dashboardService.getDashboard(auth, limit, index));
  }

  @PostMapping
  public ResponseEntity<Operation> createNew (
    @AuthenticationPrincipal User auth,
    @RequestBody Operation operation
  ) {
    return ResponseEntity.ok(operationService.create(auth, operation));
  }

  @PutMapping("{id}")
  public ResponseEntity<Operation> update (
    @AuthenticationPrincipal User auth,
    @PathVariable Long id,
    @RequestBody Operation operation
  ) {
    return ResponseEntity.ok(operationService.update(auth, id, operation));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> delete (
    @AuthenticationPrincipal User auth,
    @PathVariable Long id
  ) {
    return ResponseEntity.ok(operationService.delete(auth, id));
  }
}
