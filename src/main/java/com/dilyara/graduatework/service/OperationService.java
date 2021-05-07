package com.dilyara.graduatework.service;

import com.dilyara.graduatework.entity.Operation;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.exception.ForbiddenException;
import com.dilyara.graduatework.exception.NotFoundException;
import com.dilyara.graduatework.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {
  private final OperationRepo operationRepo;
  private final OperationCategoryService operationCategoryService;

  public Operation findById (User user, Long id) {
    Operation operation = findById(id);

    if (operation.getCategory().getUser().equals(user)) {
      return operation;
    }

    throw new ForbiddenException();
  }

  public Operation findById (Long id) {
    return operationRepo.findById(id).orElseThrow(NotFoundException::new);
  }

  public List<Operation> findAll (User user, String type, String index) {
    return operationRepo.findAll(user, Integer.parseInt(type), LocalDate.now().withDayOfMonth(1).plus(Integer.parseInt(index), ChronoUnit.MONTHS));
  }

  public Operation create (User user, Operation operation) {
    if (operation.getCategory().getId() == null) {
      operation.setCategory(operationCategoryService.save(user, operation.getCategory().getString(), operation.getCategory().getType()));
    }

    return operationRepo.save(operation);
  }

  public Operation update (User user, Long id, Operation operation) {
    Operation operationFromDb = findById(user, id);
    BeanUtils.copyProperties(operation, operationFromDb, "id");
    return create(user, operationFromDb);
  }

  public Boolean delete (User user, Long id) {
    operationRepo.delete(findById(user, id));
    return true;
  }
}
