package com.dilyara.graduatework.repo;

import com.dilyara.graduatework.entity.OperationGroup;
import com.dilyara.graduatework.entity.Operation;
import com.dilyara.graduatework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepo extends JpaRepository<Operation, Long> {
  @Query(value = "select op from Operation op where op.category.user = :user and op.category.type = :type and extract(month from cast(op.date timestamp)) = extract(month from cast(:date timestamp)) and extract(year from cast(op.date timestamp)) = extract(year from cast(:date timestamp))")
  List<Operation> findAll (
    @Param("user") User user,
    @Param("type") Integer type,
    @Param("date") LocalDate date
  );

  @Query(value = "select new com.dilyara.graduatework.entity.OperationGroup(op.category.string, sum(op.sum)) from Operation op where op.category.user = :user and op.category.type = :type group by op.category.string")
  List<OperationGroup> findAllByType (
    @Param("user") User user,
    @Param("type") Integer type
  );
}
