package com.hongik.ce.f23.team1.screw.user.repository;

import com.hongik.ce.f23.team1.screw.user.domain.Password;
import java.util.Optional;

public interface PasswordRepository {

  void save(Password password);

  Optional<Password> findByUserId(long userId);
}
