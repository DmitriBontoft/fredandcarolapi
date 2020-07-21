package com.fredandcarol.fredandcarolapi;
import com.fredandcarol.fredandcarolapi.auth.jwt.TokenAuthenticationService;
import com.fredandcarol.fredandcarolapi.auth.UserAuthenticationService;
import com.fredandcarol.fredandcarolapi.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private UserAuthenticationService authentication;

  public UserController(TokenAuthenticationService authentication) {
    this.authentication = authentication;
  }

  @GetMapping("/current")
  User getCurrent(@AuthenticationPrincipal final User user) {
    return user;
  }

  @GetMapping("/logout")
  boolean logout(@AuthenticationPrincipal final User user) {
    authentication.logout(user);
    return true;
  }

}
