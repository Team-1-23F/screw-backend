package com.hongik.ce.f23.team1.screw.user.repository;

import com.hongik.ce.f23.team1.screw.user.domain.Password;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
public class PasswordJpaRepository implements PasswordRepository {

  private final EntityManager em;

  public PasswordJpaRepository(EntityManager em) {
    this.em = em;
  }

  public void save(Password password) {
    em.persist(password);
  }

  public Optional<Password> findByUserId(long userId) {
    List<Password> passwords = em.createQuery("SELECT user FROM Password password WHERE password.user.id = :userId",
            Password.class)
        .setParameter("userId", userId)
        .getResultList();

    return passwords.stream().findAny();
  }
}
