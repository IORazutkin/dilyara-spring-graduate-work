package com.dilyara.graduatework.service;

import com.dilyara.graduatework.dto.facebook.*;
import com.dilyara.graduatework.entity.InstagramPost;
import com.dilyara.graduatework.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacebookStatService {
  public List<TargetDto> getWeeklyTarget (User user, String index) {
    if (!user.checkUserValid()) {
      return null;
    }

    LocalDate current = LocalDate.now().plusWeeks(Integer.parseInt(index));
    LocalDate next = LocalDate.now().plusWeeks(Integer.parseInt(index) + 1);
    TemporalField fieldRu = WeekFields.ISO.dayOfWeek();

    Timestamp weekFirst = Timestamp.valueOf(current.with(fieldRu, 1).atStartOfDay());
    Timestamp weekLast = Timestamp.valueOf(next.with(fieldRu, 1).atStartOfDay());

    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      FacebookService.API_GRAPH_ENDPOINT + "/" + user.getInstagramId() + "/insights"
    )
      .queryParam("metric", "impressions")
      .queryParam("period", "day")
      .queryParam("since", weekFirst.getTime() / 1000L)
      .queryParam("until", weekLast.getTime() / 1000L)
      .queryParam("access_token", user.getToken().getAccessToken());
    InsightDto userInsightDto = restTemplate.getForObject(
      builder.toUriString(),
      InsightDto.class
    );

    if (userInsightDto != null) {
      return userInsightDto.getData().get(0).getTargetDtoList();
    }

    return null;
  }

  public StatDto getStat (User user) {
    if (!user.checkUserValid()) {
      return null;
    }

    StatDto statDto = new StatDto();
    statDto.setAudience(getAudienceDto(user));
    statDto.setPage(getPageDto(user));

    return statDto;
  }

  public PageDto getPageDto (User user) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      FacebookService.API_GRAPH_ENDPOINT + "/" + user.getInstagramId()
    )
      .queryParam("fields", "media_count,followers_count,follows_count")
      .queryParam("access_token", user.getToken().getAccessToken());

    return restTemplate.getForObject(
      builder.toUriString(),
      PageDto.class
    );
  }

  public AudienceDto getAudienceDto (User user) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      FacebookService.API_GRAPH_ENDPOINT + "/" + user.getInstagramId() + "/insights"
    )
      .queryParam("metric", "audience_city,audience_gender_age")
      .queryParam("period", "lifetime")
      .queryParam("access_token", user.getToken().getAccessToken());
    InsightDto insightDto = restTemplate.getForObject(
      builder.toUriString(),
      InsightDto.class
    );

    if (insightDto != null) {
      Map<String, Integer> audienceCity = insightDto.getData().size() > 0 ? (Map<String, Integer>) insightDto.getData().get(0).getValues().get(0).getValue() : null;
      Map<String, Integer> audienceGenderAge = insightDto.getData().size() > 1 ? (Map<String, Integer>) insightDto.getData().get(1).getValues().get(0).getValue() : null;

      if (audienceCity != null) {
        audienceCity = getSortEndLimitMap(audienceCity);
      }

      if (audienceGenderAge != null) {
        audienceGenderAge = getSortEndLimitMap(audienceGenderAge);
      }

      AudienceDto audienceDto = new AudienceDto();
      audienceDto.setAudienceCity(audienceCity);
      audienceDto.setAudienceGenderAge(audienceGenderAge);

      return audienceDto;
    }

    return null;
  }

  private Map<String, Integer> getSortEndLimitMap (Map<String, Integer> map) {
    return map.entrySet().stream()
      .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3)
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (oldValue, newValue) -> oldValue, HashMap::new
      )
    );
  }

  public InstagramPostListDto getPosts (User user, String limit, String after) {
    if (!user.checkUserValid()) {
      return null;
    }

    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      FacebookService.API_GRAPH_ENDPOINT + "/" + user.getInstagramId() + "/media"
    )
      .queryParam("limit", limit)
      .queryParam("after", after)
      .queryParam("fields", "like_count,comments_count,media_url,timestamp,saved")
      .queryParam("access_token", user.getToken().getAccessToken());
    PostListDto postListDto = restTemplate.getForObject(
      builder.toUriString(),
      PostListDto.class
    );

    if (postListDto != null) {
      return postListDto.toInstagramPostListDto();
    }

    return null;
  }

  public InstagramPost getPost (User user, InstagramPost post) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      FacebookService.API_GRAPH_ENDPOINT + "/" + post.getId() + "/insights"
    )
      .queryParam("metric", "impressions,saved")
      .queryParam("access_token", user.getToken().getAccessToken());
    InsightDto insightDto = restTemplate.getForObject(
      builder.toUriString(),
      InsightDto.class
    );

    if (insightDto != null) {
      post.setImpressionsCount((Integer) insightDto.getData().get(0).getValues().get(0).getValue());
      post.setSavedCount((Integer) insightDto.getData().get(1).getValues().get(0).getValue());
    }

    return post;
  }

  public WeeklyStatDto getWeeklyStat (User user) {
    if (!user.checkUserValid()) {
      return null;
    }

    LocalDate previous = LocalDate.now().minusWeeks(1);
    LocalDate current = LocalDate.now();
    LocalDate next = LocalDate.now().plusWeeks(1);
    TemporalField fieldRu = WeekFields.ISO.dayOfWeek();

    Timestamp weekPrevious = Timestamp.valueOf(previous.with(fieldRu, 1).atStartOfDay());
    Timestamp weekCurrent = Timestamp.valueOf(current.with(fieldRu, 1).atStartOfDay());
    Timestamp weekNext = Timestamp.valueOf(next.with(fieldRu, 1).atStartOfDay());

    WeeklyStatDto weeklyStat = new WeeklyStatDto();
    weeklyStat.setPrevious(new WeeklyStatDto.Stat(Objects.requireNonNull(getWeeklyStatParams(user, weekPrevious.getTime(), weekCurrent.getTime()))));
    weeklyStat.setCurrent(new WeeklyStatDto.Stat(Objects.requireNonNull(getWeeklyStatParams(user, weekCurrent.getTime(), weekNext.getTime()))));

    return weeklyStat;
  }

  private List<Integer> getWeeklyStatParams (User user, Long start, Long finish) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      FacebookService.API_GRAPH_ENDPOINT + "/" + user.getInstagramId() + "/insights"
    )
      .queryParam("metric", "impressions,reach,profile_views,follower_count")
      .queryParam("period", "day")
      .queryParam("since", start / 1000L)
      .queryParam("until", finish / 1000L)
      .queryParam("access_token", user.getToken().getAccessToken());
    InsightDto insightDto = restTemplate.getForObject(
      builder.toUriString(),
      InsightDto.class
    );

    if (insightDto != null) {
      return insightDto.getData().stream().map(InsightDto.InsightData::getValueSum).collect(Collectors.toList());
    }

    return null;
  }
}
