package com.hongik.ce.f23.team1.screw.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hongik.ce.f23.team1.screw.global.exception.ScrewException;
import com.hongik.ce.f23.team1.screw.user.application.UserService;
import com.hongik.ce.f23.team1.screw.user.domain.Password;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.domain.User.LoginMethod;
import com.hongik.ce.f23.team1.screw.user.repository.PasswordRepository;
import com.hongik.ce.f23.team1.screw.user.repository.UserRepository;
import com.hongik.ce.f23.team1.screw.user.ui.dto.SignUpRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordRepository passwordRepository;

  @InjectMocks
  private UserService userService;


  @Test
  void 회원가입() {
    // given: 회원가입 로직을 위한 signUpRequest를 제공한다.
    //        signUpRequest를 기반으로 기대값 expectedUser를 준비한다.
    SignUpRequest signUpRequest = new SignUpRequest(
        "test@gmail.com",
        "rawpassword",
        "test",
        "job"
    );

    User expectedUser = User.builder()
        .email(signUpRequest.getEmail())
        .nickname(signUpRequest.getNickname())
        .job(signUpRequest.getJob())
        .loginMethod(LoginMethod.PASSWORD)
        .build();

    when(userRepository.save(any())).thenReturn(
        expectedUser
    );
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.empty())
        .thenReturn(Optional.of(expectedUser));
    when(userRepository.findByNickname(anyString())).thenReturn(Optional.empty());

    // when: 회원가입 로직을 실행한다.
    User signUpUser = userService.signUp(expectedUser, signUpRequest.getPassword());

    // then: 회원가입 로직이 반환한 User와 기대값이 일치한다.
    assertThat(signUpUser.getId()).isEqualTo(expectedUser.getId());
    assertThat(signUpUser.getNickname()).isEqualTo(expectedUser.getNickname());
    assertThat(signUpUser.getEmail()).isEqualTo(expectedUser.getEmail());
    assertThat(signUpUser.getJob()).isEqualTo(expectedUser.getJob());
    assertThat(signUpUser.getLoginMethod()).isEqualTo(LoginMethod.PASSWORD);
  }

  @Test
  void 회원가입_이메일_중복() {
    // given: 회원가입 로직을 위한 signUpRequest를 제공한다.
    //        signUpRequest를 기반으로 기대값 expectedUser를 준비한다.
    //        userRepository.findByEmail()은 User 객체를 반환한다.
    SignUpRequest signUpRequest = new SignUpRequest(
        "test@gmail.com",
        "rawpassword",
        "test",
        "job"
    );

    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(
            User.builder().build()
        ));

    // when: 회원가입 로직을 실행한다.
    Throwable throwable = catchThrowable(() -> userService.signUp(
        signUpRequest.toUserEntity(LoginMethod.PASSWORD),
        signUpRequest.getPassword())
    );

    // then: 이메일 중복 예외가 발생한다.
    assertThat(throwable)
        .isInstanceOf(ScrewException.class)
        .hasMessage("이미 사용중인 이메일입니다.");
  }

  @Test
  void 회원가입_닉네임_중복() {
    // given: 회원가입 로직을 위한 signUpRequest를 제공한다.
    //        signUpRequest를 기반으로 기대값 expectedUser를 준비한다.
    //        userRepository.findByNickname()은 User 객체를 반환한다.
    SignUpRequest signUpRequest = new SignUpRequest(
        "test@gmail.com",
        "rawpassword",
        "test",
        "job"
    );

    when(userRepository.findByNickname(anyString()))
        .thenReturn(Optional.of(
            User.builder().build()
        ));

    // when: 회원가입 로직을 실행한다.
    Throwable throwable = catchThrowable(() -> userService.signUp(
        signUpRequest.toUserEntity(LoginMethod.PASSWORD),
        signUpRequest.getPassword())
    );

    // then: 닉네임 중복 예외가 발생한다.
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("이미 사용중인 닉네임입니다.");
  }


  @Test
  void 로그인_성공() {
    // given: 회원가입 로직을 위한 SignInRequest 제공한다.
    //        Repository가 반환하는 mockUser, mockPassword를 제공한다.
    String email = "test@gmail.com";
    String password = "rawpassword";

    User mockUser = mock(User.class);
    Password mockPassword = mock(Password.class);

    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(mockUser));
    when(passwordRepository.findByUserId(anyLong()))
        .thenReturn(Optional.of(mockPassword));

    when(mockUser.getId()).thenReturn(1L);
    when(mockUser.getEmail()).thenReturn(email);
    when(mockPassword.getPassword()).thenReturn(password);

    // when: 로그인 로직을 실행한다.
    final User user = userService.login(email, password);

    // when: 로그인에 성공한다.
    assertThat(user.getEmail()).isEqualTo(email);
  }

  @Test
  void 로그인_실패_아이디_없음() {
    // given: 회원가입 로직을 위한 SignInRequest 제공한다.
    //        signInRequest.email은 존재하지 않는 이메일이다.
    String email = "test@gmail.com";
    String password = "rawpassword";

    when(userRepository.findByEmail(email))
        .thenReturn(Optional.empty());

    // when: 로그인 로직을 실행한다.
    Throwable throwable = catchThrowable(
        () -> userService.login(email, password));

    // when: 로그인에 실패한다.
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("존재하지 않는 사용자입니다.");
  }

  @Test
  void 로그인_실패_비밀번호_불일치() {
    // given: 회원가입 로직을 위한 SignInRequest 제공한다.
    //        Repository가 반환하는 mockUser, mockPassword를 제공한다.
    String email = "test@gmail.com";
    String password = "rawpassword";

    User mockUser = mock(User.class);
    Password mockPassword = mock(Password.class);

    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(mockUser));
    when(passwordRepository.findByUserId(anyLong()))
        .thenReturn(Optional.of(mockPassword));

    when(mockUser.getId()).thenReturn(1L);
    when(mockPassword.getPassword()).thenReturn("");

    // when: 로그인 로직을 실행한다.
    Throwable throwable = catchThrowable(
        () -> userService.login(email, password));

    // when: 로그인에 실패한다.
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("비밀번호가 일치하지 않습니다.");

  }
}
