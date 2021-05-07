package com.dilyara.graduatework.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountsDto {
  List<Account> data;
  PagingDto paging;

  @Data
  public static class Account {
    @JsonProperty("access_token")
    String accessToken;
    String category;
    String name;
    String id;
    List<String> tasks;
    @JsonProperty("category_list")
    List<Category> categoryList;

    @Data
    public static class Category {
      String id;
      String name;
    }
  }
}
