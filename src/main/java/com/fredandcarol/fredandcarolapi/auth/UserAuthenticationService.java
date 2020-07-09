package com.fredandcarol.fredandcarolapi.auth;

import com.fredandcarol.fredandcarolapi.user.User;
import com.fredandcarol.fredandcarolapi.user.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

  private Users users;

  public UserAuthenticationService(Users users) {
    this.users = users;
  }

  public Optional<String> login(String username, String password) {
    final String uuid = UUID.randomUUID().toString();
    users.save(new User(uuid, username, password));
    return Optional.of(uuid);
  }

  public void logout(User user) {

  }

  public Optional<User> findByToken(final String token) {
    return users.find(token);
  }
}