package com.dilyara.graduatework.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageDto {
  Integer mediaCount;
  Integer followersCount;
  Integer followsCount;

  @JsonProperty("mediaCount")
  public Integer getMediaCount () {
    return mediaCount;
  }

  @JsonProperty("media_count")
  public void setMediaCount (Integer mediaCount) {
    this.mediaCount = mediaCount;
  }

  @JsonProperty("followersCount")
  public Integer getFollowersCount () {
    return followersCount;
  }

  @JsonProperty("followers_count")
  public void setFollowersCount (Integer followersCount) {
    this.followersCount = followersCount;
  }

  @JsonProperty("followsCount")
  public Integer getFollowsCount () {
    return followsCount;
  }

  @JsonProperty("follows_count")
  public void setFollowsCount (Integer followsCount) {
    this.followsCount = followsCount;
  }
}
