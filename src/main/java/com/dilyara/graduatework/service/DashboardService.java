package com.dilyara.graduatework.service;

import com.dilyara.graduatework.dto.operation_dashboard.CostStat;
import com.dilyara.graduatework.dto.operation_dashboard.MoneyFlow;
import com.dilyara.graduatework.dto.operation_dashboard.OperationDashboardDto;
import com.dilyara.graduatework.entity.OperationGroup;
import com.dilyara.graduatework.entity.Operation;
import com.dilyara.graduatework.entity.Type;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
  private final OperationService operationService;
  private final OperationRepo operationRepo;

  public OperationDashboardDto getDashboard (User user, String limit, String index) {
    OperationDashboardDto operationDashboardDto = new OperationDashboardDto();
    operationDashboardDto.setMoneyFlow(
      getMoneyFlow(user, Integer.parseInt(limit), Integer.parseInt(index))
    );
    operationDashboardDto.setCostStat(getCostStat(user));

    return operationDashboardDto;
  }

  private List<MoneyFlow> getMoneyFlow (User user, Integer limit, Integer index) {
    List<MoneyFlow> result = new ArrayList<>();

    for (int i = 0; i < limit; i++) {
      int elementIndex = limit - i - 1 - index;
      MoneyFlow moneyFlow = new MoneyFlow();
      LocalDate localDate = LocalDate.now().withDayOfMonth(1).minus(elementIndex, ChronoUnit.MONTHS);
      moneyFlow.setDate(localDate);

      List<Operation> profitList = operationService.findAll(user, String.valueOf(Type.PROFIT.getId()), String.valueOf(-elementIndex));
      List<Operation> costList = operationService.findAll(user, String.valueOf(Type.COST.getId()), String.valueOf(-elementIndex));

      moneyFlow.setProfit(profitList.stream().map(Operation::getSum).reduce(Float::sum).orElse(0f));
      moneyFlow.setCost(costList.stream().map(Operation::getSum).reduce(Float::sum).orElse(0f));

      result.add(moneyFlow);
    }

    return result;
  }

  private List<CostStat> getCostStat (User user) {
    List<OperationGroup> map = operationRepo.findAllByType(user, Type.COST.getId());
    List<CostStat> result = new ArrayList<>();
    map.forEach(item -> result.add(new CostStat(item.getString(), item.getSum())));

    return result;
  }
}
