package com.dilyara.graduatework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OperationCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String string;

  @Column
  private Integer type;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public OperationCategory () {}

  public OperationCategory (User user, String string, Integer type) {
    this.string = string;
    this.user = user;
    this.type = type;
  }
}
