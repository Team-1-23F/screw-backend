package com.hongik.ce.f23.team1.screw.config.security;

import com.hongik.ce.f23.team1.screw.user.domain.User;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {

  public static final String ROLE = "User";

  private final User user;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of((GrantedAuthority) () -> ROLE);
  }

  @Override
  public String getPassword() {
    return user.getPassword().getPassword();
  }


  @Override
  public String getUsername() {
    return user.getId().toString();
  }

  public Long getUserId() {
    return user.getId();
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
