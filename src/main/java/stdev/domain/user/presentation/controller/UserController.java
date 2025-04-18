package stdev.domain.user.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import stdev.domain.user.application.service.UserService;
import stdev.domain.user.domain.repository.UserRepository;
import stdev.domain.user.presentation.dto.req.MypageRequest;
import stdev.domain.user.presentation.dto.req.UserJoinDto;
import stdev.domain.user.presentation.dto.req.UserLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stdev.domain.user.presentation.dto.res.UserMypageResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public ResponseEntity<UserMypageResponse> getMyPage(@AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(userService.mypageGet(userId));
    }


    @PatchMapping("/mypage")
    public void updateMyPage(
            @RequestBody MypageRequest req,
            @AuthenticationPrincipal String userId) {
        userService.mypageUpdate(req, userId);
    }
}