package com.hongik.ce.f23.team1.screw.user.application;

import com.hongik.ce.f23.team1.screw.user.domain.Password;
import com.hongik.ce.f23.team1.screw.user.domain.User;
import com.hongik.ce.f23.team1.screw.user.domain.User.LoginMethod;
import com.hongik.ce.f23.team1.screw.user.dto.SignUpRequest;
import com.hongik.ce.f23.team1.screw.user.repository.PasswordRepository;
import com.hongik.ce.f23.team1.screw.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


  public User signUp(SignUpRequest signUpRequest) throws IllegalStateException {
    User user = signUpRequest.toUserEntity(LoginMethod.PASSWORD);

    validateDuplicateUser(user);

    User newUser = userRepository.save(user);

    passwordRepository.save(Password.builder()
        .user(newUser)
        .password(signUpRequest.getPassword())
        .build());

    return newUser;
  }

  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
  }

  private void validateDuplicateUser(User user) {
    validateDuplicateEmail(user);
    validateDuplicateNickname(user);
  }

  private void validateDuplicateEmail(User user) {
    userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
      throw new IllegalStateException("이미 사용중인 이메일입니다.");
    });
  }

  private void validateDuplicateNickname(User user) {
    userRepository.findByNickname(user.getNickname()).ifPresent(u -> {
      throw new IllegalStateException("이미 사용중인 닉네임입니다.");
    });
  }

}
