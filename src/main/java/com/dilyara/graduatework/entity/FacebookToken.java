package com.dilyara.graduatework.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class FacebookToken {
  @Id
  private String accessToken;

  @Column
  private String type;
}
