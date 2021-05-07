package com.dilyara.graduatework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Note {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private Integer type;

  @Column
  private String string;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
