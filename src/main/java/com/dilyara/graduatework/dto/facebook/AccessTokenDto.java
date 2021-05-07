package com.dilyara.graduatework.dto.facebook;

import com.dilyara.graduatework.entity.FacebookToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessTokenDto {
  @JsonProperty("access_token")
  String accessToken;

  @JsonProperty("token_type")
  String tokenType;

  public FacebookToken toFacebookToken () {
    FacebookToken token = new FacebookToken();
    token.setType(this.tokenType);
    token.setAccessToken(this.accessToken);

    return token;
  }
}
