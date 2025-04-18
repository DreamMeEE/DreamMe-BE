package stdev.domain.oauth2.application.service.impl;

import stdev.domain.oauth2.application.service.KakaoUserService;
import stdev.domain.oauth2.presentation.dto.response.OAuth2UserResponse;
import stdev.global.infra.feignclient.KakaoOAuth2UserFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoUserServiceImpl implements KakaoUserService {

    private final KakaoOAuth2UserFeignClient KakaoOAuth2UserFeignClient;

    @Override
    public OAuth2UserResponse getUser(String accessToken) {
        OAuth2UserResponse response = KakaoOAuth2UserFeignClient.getUserInfo("Bearer " + accessToken);
        log.info("카카오 API 응답: {}", response);
        return response;
    }
}
