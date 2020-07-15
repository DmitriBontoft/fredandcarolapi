package com.fredandcarol.fredandcarolapi.auth.jwt;

import com.fredandcarol.fredandcarolapi.user.User;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService implements UserAuthenticationService {

  private HashMap<String, User> users = new HashMap<>();
  private JWTTokenService tokenService;

  public TokenAuthenticationService(
      JWTTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public Optional<String> login(String username, String password) {
    return findUserByUsername(username)
        .filter(user -> Objects.equals(password, user.getPassword()))
        .map(user -> tokenService.expiring(ImmutableMap.of("username", username)));
  }

  private Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(users.get(username));
  }

  @Override
  public Optional<User> findByToken(final String token) {
    return Optional
        .of(tokenService.verify(token))
        .map(map -> map.get("username"))
        .flatMap(this::findUserByUsername);
  }

  @Override
  public void logout(final User user) {
    // Nothing to doy
  }
}