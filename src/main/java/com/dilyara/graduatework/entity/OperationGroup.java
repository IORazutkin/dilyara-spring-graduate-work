package com.dilyara.graduatework.entity;

import lombok.Data;

@Data
public class OperationGroup {
  private String string;
  private Double sum;

  public OperationGroup (String string, Double sum) {
    this.string = string;
    this.sum = sum;
  }
}
