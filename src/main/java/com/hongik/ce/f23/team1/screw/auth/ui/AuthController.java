package com.hongik.ce.f23.team1.screw.auth.ui;


import com.hongik.ce.f23.team1.screw.auth.service.AuthService;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.JoinRequest;
import com.hongik.ce.f23.team1.screw.auth.ui.dto.TokenResponse;
import com.hongik.ce.f23.team1.screw.config.security.CustomUserDetails;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewException;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;
import com.hongik.ce.f23.team1.screw.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  private final JwtUtil jwtUtil;
  private final AuthService authService;


  @PostMapping("/join")
  ResponseEntity<Void> join(@Valid @RequestBody JoinRequest joinRequest) {
    authService.join(joinRequest);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/logout")
  ResponseEntity<Void> logout() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

    authService.removeRefreshToken(userId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh")
  ResponseEntity<TokenResponse> refresh(HttpServletRequest request) {
    final String refreshToken = request.getHeader("Refresh");

    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new ScrewException(ScrewExceptionInfo.BAD_REQUEST);
    }

    final Long userId = jwtUtil.getUserIdFromRefreshToken(refreshToken);

    final boolean canRefresh = authService.checkRefreshToken(userId, refreshToken);

    if (canRefresh) {
      final String newAccessToken = jwtUtil.createAccessToken(userId);
      final String newRefreshToken = jwtUtil.createRefreshToken(userId);

      return ResponseEntity.ok().body(new TokenResponse(newAccessToken, newRefreshToken));
    }

    throw new ScrewException(ScrewExceptionInfo.UNKNOWN_ERROR);
  }
}
