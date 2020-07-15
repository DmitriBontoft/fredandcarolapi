package com.fredandcarol.fredandcarolapi.auth;

import com.fredandcarol.fredandcarolapi.user.User;
import com.fredandcarol.fredandcarolapi.user.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SimpleAuthenticationService implements UserAuthenticationService {

  private Users users;

  public SimpleAuthenticationService(Users users) {
    this.users = users;
  }

  @Override
  public Optional<String> login(String username, String password) {
    final String uuid = UUID.randomUUID().toString();
    users.save(new User(uuid, username, password));
    return Optional.of(uuid);
  }

  @Override
  public void logout(User user) {

  }

  @Override
  public Optional<User> findByToken(final String token) {
    return users.find(token);
  }
}
