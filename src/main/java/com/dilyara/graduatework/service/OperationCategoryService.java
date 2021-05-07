package com.dilyara.graduatework.service;

import com.dilyara.graduatework.entity.OperationCategory;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.repo.OperationCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationCategoryService {
  private final OperationCategoryRepo operationCategoryRepo;

  public List<OperationCategory> findAll (User user) {
    return operationCategoryRepo.findAllByUser(user);
  }

  public OperationCategory save (User user, String string, Integer type) {
    return operationCategoryRepo.save(new OperationCategory(user, string, type));
  }
}
