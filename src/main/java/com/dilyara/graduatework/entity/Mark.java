package com.dilyara.graduatework.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Mark {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String event;

  @Column
  private LocalDate date;

  @Column
  private Integer type;

  @Column
  private String comment;

  @Column
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
