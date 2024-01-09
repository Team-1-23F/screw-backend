package com.hongik.ce.f23.team1.screw.user.repository;

import com.hongik.ce.f23.team1.screw.user.domain.Password;
import jakarta.persistence.EntityManager;
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

    log.debug("password = {}", password);
    em.persist(password);
  }
}
