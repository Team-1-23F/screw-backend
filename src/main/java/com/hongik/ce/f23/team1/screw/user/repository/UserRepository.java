package com.hongik.ce.f23.team1.screw.user.repository;

import com.hongik.ce.f23.team1.screw.user.domain.User;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  Optional<User> findByNickname(String nickname);
}
