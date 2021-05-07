package com.dilyara.graduatework.dto.facebook;

import com.dilyara.graduatework.entity.InstagramPost;
import lombok.Data;

import java.util.List;

@Data
public class InstagramPostListDto {
  List<InstagramPost> posts;
  String after;
}
