package com.fredandcarol.fredandcarolapi.auth.jwt;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;


public class TokenAuthenticationService implements UserAuthenticationService {
  private Map<String, User> users = new HashMap<>();
  private TokenService tokenService;


  @Override
  public Optional<User> login(String username, String password){
    return findUserByUsername(username)
        .filter(user -> Objects.equals(password, user.getPassword()))
        .map(user -> tokenService.expiring(ImmutableMap.of("username", username)));
  }

  private Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(users.get(username));
  }
}
