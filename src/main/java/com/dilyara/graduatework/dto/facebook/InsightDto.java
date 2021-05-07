package com.dilyara.graduatework.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class InsightDto {
  List<InsightData> data;
  PagingDto paging;

  @Data
  public static class InsightData {
    String name;
    String period;
    List<Value> values;
    String title;
    String description;
    String id;

    public Integer getValueSum () {
      return values.stream().map(el -> (Integer) el.value).reduce(Integer::sum).orElse(0);
    }

    public List<TargetDto> getTargetDtoList () {
      return this.values.stream().map(Value::toTargetDto).collect(Collectors.toList());
    }

    @Data
    public static class Value {
      Object value;
      @JsonProperty("end_time")
      String endTime;

      TargetDto toTargetDto () {
        TargetDto targetDto = new TargetDto();
        targetDto.setValue((Integer) this.value);
        targetDto.setDate(this.endTime);

        return targetDto;
      }
    }
  }
}
