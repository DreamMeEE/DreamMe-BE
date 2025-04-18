package stdev.domain.oauth2.application.service;

import stdev.domain.oauth2.presentation.dto.response.OAuth2TokenResponse;

public interface KakaoAccessTokenAndRefreshTokenService {
    OAuth2TokenResponse getAccessTokenAndRefreshToken(String code);
    OAuth2TokenResponse refreshAccessToken(String refreshToken);
}
