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
      this.impressions = params.get(0);
      this.reach = params.get(1);
      this.profileViews = params.get(2);
      this.followerCount = params.get(3);
    }
  }
}
