package com.dilyara.graduatework.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstagramAccountDto {
  @JsonProperty("instagram_business_account")
  InstagramBusinessAccount instagramBusinessAccount;
  String id;

  @Data
  public static class InstagramBusinessAccount {
    String id;
  }
}
