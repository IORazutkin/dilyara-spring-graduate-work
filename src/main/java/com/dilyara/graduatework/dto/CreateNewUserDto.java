package com.dilyara.graduatework.dto;

import com.dilyara.graduatework.entity.User;
import lombok.Data;

@Data
public class CreateNewUserDto {
  String fullName;
  String username;
  String password;

  public User toUser () {
    User user = new User();
    user.setFullName(this.fullName);
    user.setUsername(this.username);
    user.setPassword(this.password);

    return user;
  }
}
