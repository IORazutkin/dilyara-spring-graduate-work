package com.dilyara.graduatework.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto {
  String oldPassword;
  String newPassword;
  String confirmPassword;

  public boolean isPasswordEquals () {
    return this.newPassword.equals(this.confirmPassword);
  }

  public boolean isNewPasswordValid () {
    return !this.oldPassword.equals(this.newPassword);
  }
}
