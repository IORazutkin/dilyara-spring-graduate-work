package com.dilyara.graduatework.dto.operation_dashboard;

import lombok.Data;

import java.util.List;

@Data
public class OperationDashboardDto {
  List<MoneyFlow> moneyFlow;
  List<CostStat> costStat;
}
