package com.dilyara.graduatework.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDto {
  String fullName;
  LocalDate businessDate;
  Float startCapital;
}
