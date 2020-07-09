package com.fredandcarol.fredandcarolapi.user;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class Users {

  HashMap<String, User> users = new HashMap<String, User>();

  public User save(User user) {
    return users.put(user.getId(), user);
  }

  public Optional<User> findByUsername(String username) {
    return users
        .values()
        .stream()
        .filter(u -> Objects.equals(username, u.getUsername()))
        .findFirst();
  }

  public Optional<User> find(String token) {
    return null;
  }
}

