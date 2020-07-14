package com.fredandcarol.fredandcarolapi.auth.jwt;

import java.util.Optional;
import org.springframework.security.core.userdetails.User;

public interface UserAuthenticationService {

  Optional<User> login(String username, String password);
}
