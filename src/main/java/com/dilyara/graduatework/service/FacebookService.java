package com.dilyara.graduatework.service;

import com.dilyara.graduatework.dto.facebook.AccessTokenDto;
import com.dilyara.graduatework.dto.facebook.AccountsDto;
import com.dilyara.graduatework.dto.facebook.InstagramAccountDto;
import com.dilyara.graduatework.dto.facebook.InstagramInfoDto;
import com.dilyara.graduatework.entity.FacebookToken;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.repo.FacebookTokenRepo;
import com.dilyara.graduatework.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FacebookService {
  private final UserRepo userRepo;
  private final FacebookTokenRepo facebookTokenRepo;

  private static final String API_GRAPH_URL = "https://graph.facebook.com/";
  private static final String API_GRAPH_VERSION = "v10.0";
  public static final String API_GRAPH_ENDPOINT = API_GRAPH_URL + API_GRAPH_VERSION;

  @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
  private String clientId;
  @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
  private String clientSecret;
  @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
  private String redirectUri;

  public User saveNewToken (User user, String code) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_GRAPH_ENDPOINT + "/oauth/access_token")
      .queryParam("client_id", clientId)
      .queryParam("client_secret", clientSecret)
      .queryParam("redirect_uri", redirectUri)
      .queryParam("code", code);

    AccessTokenDto accessTokenDto = restTemplate.getForObject(
      builder.toUriString(),
      AccessTokenDto.class
    );

    if (accessTokenDto != null) {
      FacebookToken oldToken = user.getToken();
      user.setToken(accessTokenDto.toFacebookToken());

      String accountId = getFirstAccountId(user);
      user = saveInstagramId(user, accountId);
      user = saveInstagramUsername(user);
      user = userRepo.save(user);
      if (oldToken != null) {
        facebookTokenRepo.delete(oldToken);
      }
      return user;
    }

    return null;
  }

  public String getFirstAccountId (User user) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_GRAPH_ENDPOINT + "/me/accounts")
      .queryParam("access_token", user.getToken().getAccessToken());
    AccountsDto accountsDto = restTemplate.getForObject(
      builder.toUriString(),
      AccountsDto.class
    );

    if (accountsDto != null) {
      return accountsDto.getData().size() > 0 ? accountsDto.getData().get(0).getId() : null;
    }

    return null;
  }

  public User saveInstagramId (User user, String accountId) {
    if (accountId == null) {
      return user;
    }

    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_GRAPH_ENDPOINT + "/" + accountId)
      .queryParam("fields", "instagram_business_account")
      .queryParam("access_token", user.getToken().getAccessToken());
    InstagramAccountDto instagramAccountDto = restTemplate.getForObject(
      builder.toUriString(),
      InstagramAccountDto.class
    );

    if (instagramAccountDto != null) {
      if (instagramAccountDto.getInstagramBusinessAccount() != null) {
        user.setInstagramId(instagramAccountDto.getInstagramBusinessAccount().getId());
      }
      return user;
    }

    return null;
  }

  public User saveInstagramUsername (User user) {
    if (user.getInstagramId() == null) {
      return user;
    }

    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
      API_GRAPH_ENDPOINT + "/" + user.getInstagramId()
    )
      .queryParam("fields", "username")
      .queryParam("access_token", user.getToken().getAccessToken());
    InstagramInfoDto instagramInfoDto = restTemplate.getForObject(
      builder.toUriString(),
      InstagramInfoDto.class
    );

    if (instagramInfoDto != null) {
      user.setInstagramUsername(instagramInfoDto.getUsername());
      return user;
    }

    return null;
  }
}
