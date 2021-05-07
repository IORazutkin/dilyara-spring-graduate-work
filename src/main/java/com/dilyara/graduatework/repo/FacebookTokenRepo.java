package com.dilyara.graduatework.repo;

import com.dilyara.graduatework.entity.FacebookToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacebookTokenRepo extends JpaRepository<FacebookToken, String> {
}
