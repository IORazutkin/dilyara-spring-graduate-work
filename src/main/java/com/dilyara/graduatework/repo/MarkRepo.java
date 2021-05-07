package com.dilyara.graduatework.repo;

import com.dilyara.graduatework.entity.Mark;
import com.dilyara.graduatework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MarkRepo extends JpaRepository<Mark, Long> {
  @Query(value = "select m from Mark m where m.user = :user and extract(month from cast(m.date timestamp)) = extract(month from cast(:date timestamp)) and extract(year from cast(m.date timestamp)) = extract(year from cast(:date timestamp))")
  List<Mark> findAll (
    @Param("user") User user,
    @Param("date") LocalDate date
  );
}
