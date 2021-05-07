package com.dilyara.graduatework.dto.facebook;

import com.dilyara.graduatework.entity.InstagramPost;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class InstagramPostDto {
  @JsonProperty("like_count")
  Integer likeCount;
  @JsonProperty("comments_count")
  Integer commentsCount;
  @JsonProperty("media_url")
  String mediaUrl;
  @JsonProperty("timestamp")
  String postDate;
  String id;

  public InstagramPost toInstagramPost () {
    InstagramPost instagramPost = new InstagramPost();
    BeanUtils.copyProperties(this, instagramPost);

    return instagramPost;
  }
}
