package com.dilyara.graduatework.dto.facebook;

import lombok.Data;

@Data
public class PagingDto {
  Cursor cursors;
  String next;
  String previous;

  @Data
  public static class Cursor {
    String before;
    String after;
  }
}
