package com.hongik.ce.f23.team1.screw.user.repository;

import com.hongik.ce.f23.team1.screw.user.domain.User;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaRepository implements UserRepository {

  private final EntityManager em;

  @Autowired
  public UserJpaRepository(EntityManager em) {
    this.em = em;
  }

  public User save(User user) {
    em.persist(user);

    return user;
  }

  public Optional<User> findById(Long id) {
    User user = em.find(User.class, id);
    return Optional.ofNullable(user);
  }

  public Optional<User> findByEmail(String email) {
    List<User> userList = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
        .setParameter("email", email)
        .getResultList();

    return userList.stream().findAny();
  }

  public Optional<User> findByNickname(String nickname) {
    List<User> userList = em.createQuery("SELECT user FROM User user WHERE user.nickname = :nickname", User.class)
        .setParameter("nickname", nickname)
        .getResultList();

    return userList.stream().findAny();
  }
}
