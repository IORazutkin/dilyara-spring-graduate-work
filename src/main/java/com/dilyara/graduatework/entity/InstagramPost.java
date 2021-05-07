package com.dilyara.graduatework.entity;

import lombok.Data;

@Data
public class InstagramPost {
  private Integer likeCount;
  private Integer commentsCount;
  private Integer impressionsCount;
  private Integer savedCount;
  private String mediaUrl;
  private String postDate;
  private String id;
}
