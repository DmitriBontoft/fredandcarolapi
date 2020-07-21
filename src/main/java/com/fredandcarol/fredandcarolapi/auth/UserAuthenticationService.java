package com.fredandcarol.fredandcarolapi.auth;

import com.fredandcarol.fredandcarolapi.user.User;
import java.util.Optional;

public interface UserAuthenticationService {

  Optional<String> login(String username, String password);

  void logout(User user);

  Optional<User> findByToken(String token);

  void register(User user);
}
