package com.fredandcarol.fredandcarolapi.auth;

import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  final UserAuthenticationService auth;

  public TokenAuthenticationProvider(UserAuthenticationService auth) {
    this.auth = auth;
  }


  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {
    // There are none
  }

  @Override
  protected UserDetails retrieveUser(String s,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {
    final Object token = usernamePasswordAuthenticationToken.getCredentials();
    return Optional
        .ofNullable(token)
        .map(String::valueOf)
        .flatMap(auth::findByToken)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
  }
}
