package com.dilyara.graduatework.dto.operation_dashboard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MoneyFlow {
  LocalDate date;
  Float profit;
  Float cost;
}
