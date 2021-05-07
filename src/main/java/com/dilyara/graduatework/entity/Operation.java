package com.dilyara.graduatework.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Operation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private LocalDate date;

  @Column
  private Integer paymentType;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private OperationCategory category;

  @Column
  private Float sum;

  @Column
  private String comment;

  @Column
  private String description;
}
