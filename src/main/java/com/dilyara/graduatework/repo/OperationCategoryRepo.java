package com.dilyara.graduatework.repo;

import com.dilyara.graduatework.entity.OperationCategory;
import com.dilyara.graduatework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationCategoryRepo extends JpaRepository<OperationCategory, Long> {
  List<OperationCategory> findAllByUser (User user);
}
