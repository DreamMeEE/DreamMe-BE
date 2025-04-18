package stdev.domain.oauth2.application.service;

public interface KakaoTokenService {
    String getValidAccessToken(String userId);
}
