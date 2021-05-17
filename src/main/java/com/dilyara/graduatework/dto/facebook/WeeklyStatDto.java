package com.dilyara.graduatework.dto.facebook;

import lombok.Data;

import java.util.List;

@Data
public class WeeklyStatDto {
  Stat previous;
  Stat current;

  @Data
  public static class Stat {
    Integer impressions;
    Integer reach;
    Integer profileViews;
    Integer followerCount;

    public Stat (List<Integer> params) {
      this.impressions = params.size() >= 1 ? params.get(0) : 0;
      this.reach = params.size() >= 2 ? params.get(1) : 0;
      this.profileViews = params.size() >= 3 ? params.get(2) : 0;
      this.followerCount = params.size() >= 4 ? params.get(3) : 0;
    }
  }
}
