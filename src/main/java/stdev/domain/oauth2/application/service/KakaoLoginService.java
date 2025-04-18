package stdev.domain.oauth2.application.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public interface KakaoLoginService {
    Map<String, String> login(String code, HttpServletResponse response) throws IOException;
}
