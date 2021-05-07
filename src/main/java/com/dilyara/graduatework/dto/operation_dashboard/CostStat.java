package com.dilyara.graduatework.dto.operation_dashboard;

import lombok.Data;

@Data
public class CostStat {
  String title;
  Double value;

  public CostStat () {}

  public CostStat (String title, Double value) {
    this.title = title;
    this.value = value;
  }
}
