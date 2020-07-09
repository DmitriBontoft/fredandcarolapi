package com.fredandcarol.fredandcarolapi.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.apache.commons.lang3.StringUtils.removeStart;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.util.Optional.*;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String BEARER = "Bearer";

  public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
    super(requiresAuth);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse)
      throws AuthenticationException, IOException, ServletException {
    final String param = ofNullable(httpServletRequest.getHeader(AUTHORIZATION))
        .orElse(httpServletRequest.getParameter("t"));

    final String token = ofNullable(param)
        .map(value -> removeStart(value, BEARER))
        .map(String::trim)
        .orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));

    final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
    return getAuthenticationManager().authenticate(auth);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }
}
