package stdev.domain.user.application.service;


import stdev.domain.user.presentation.dto.req.MypageRequest;
import stdev.domain.user.presentation.dto.req.UserJoinDto;
import stdev.domain.user.presentation.dto.req.UserLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import stdev.domain.user.presentation.dto.res.UserMypageResponse;

import java.io.IOException;

public interface UserService {

    UserMypageResponse mypageGet(String userId);

    void mypageUpdate(MypageRequest req, String userId);
}