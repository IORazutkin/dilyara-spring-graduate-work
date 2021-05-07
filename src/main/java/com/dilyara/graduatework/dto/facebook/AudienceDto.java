package com.dilyara.graduatework.dto.facebook;

import lombok.Data;

import java.util.Map;

@Data
public class AudienceDto {
  Map<String, Integer> audienceCity;
  Map<String, Integer> audienceGenderAge;
}
