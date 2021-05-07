package com.dilyara.graduatework.repo;

import com.dilyara.graduatework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
  User findByUsername (String username);
}
