package stdev.domain.oauth2.application.service;

import stdev.domain.oauth2.presentation.dto.response.OAuth2TokenResponse;
import stdev.domain.oauth2.presentation.dto.response.OAuth2UserResponse;

import java.util.Map;

public interface KakaoUserCreateService {
    Map<String, String> createKakaoUser(OAuth2TokenResponse oAuth2TokenResponse, OAuth2UserResponse oAuth2UserResponse);
}
