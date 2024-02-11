package com.hongik.ce.f23.team1.screw.user.application;

import com.hongik.ce.f23.team1.screw.global.exception.ScrewException;
import com.hongik.ce.f23.team1.screw.global.exception.ScrewExceptionInfo;
import com.hongik.ce.f23.team1.screw.user.domain.Password;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.repository.PasswordRepository;
import com.hongik.ce.f23.team1.screw.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final PasswordRepository passwordRepository;

  @Autowired
  public UserService(UserRepository userRepository, PasswordRepository passwordRepository) {
    this.userRepository = userRepository;
    this.passwordRepository = passwordRepository;
  }


  public User signUp(@NonNull User user, @NonNull String password) throws IllegalStateException {
    validateDuplicateUser(user);

    final User newUser = userRepository.save(user);

    passwordRepository.save(Password.builder()
        .user(newUser)
        .password(password)
        .build());

    return newUser;
  }

  public User login(@NonNull String email, @NonNull String password) {
    final User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    checkPassword(user.getId(), password);

    return user;
  }

  private void validateDuplicateUser(User user) {
    validateDuplicateEmail(user);
    validateDuplicateNickname(user);
  }

  private void validateDuplicateEmail(@NonNull User user) {
    userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
      throw new ScrewException(ScrewExceptionInfo.DUPLICATED_EMAIL);
    });
  }

  private void validateDuplicateNickname(@NonNull User user) {
    userRepository.findByNickname(user.getNickname()).ifPresent(u -> {
      throw new ScrewException(ScrewExceptionInfo.DUPLICATED_NICKNAME);
    });
  }

  private void checkPassword(long userId, String enteredPassword) {
    final String password = passwordRepository.findByUserId(userId)
        .orElseThrow(
            () -> new IllegalStateException("비밀번호를 찾을 수 없습니다.")
        )
        .getPassword();

    if (!password.equals(enteredPassword)) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
  }
}
