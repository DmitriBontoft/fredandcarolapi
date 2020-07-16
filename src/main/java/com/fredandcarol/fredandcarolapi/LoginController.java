package com.fredandcarol.fredandcarolapi;

import com.fredandcarol.fredandcarolapi.auth.SimpleAuthenticationService;
import com.fredandcarol.fredandcarolapi.auth.UserAuthenticationService;
import com.fredandcarol.fredandcarolapi.auth.jwt.TokenAuthenticationService;
import com.fredandcarol.fredandcarolapi.user.User;
import com.fredandcarol.fredandcarolapi.user.Users;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

  private Users users;
  private UserAuthenticationService authentication;

  public LoginController(Users users,
      TokenAuthenticationService authentication) {
    this.users = users;
    this.authentication = authentication;
  }

  @PostMapping("/register")
  public String register(
      @RequestParam("username") final String username,
      @RequestParam("password") final String password) {

    this.authentication.register(new User(username, username, password));

    return login(username, password);
  }

  @PostMapping("/login")
  public String login(
      @RequestParam("username") final String username,
      @RequestParam("password") final String password) {
    return authentication
        .login(username, password)
        .orElseThrow(() -> new RuntimeException("Invalid login and/or password"));
  }
}
