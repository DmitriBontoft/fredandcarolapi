package com.fredandcarol.fredandcarolapi.auth.jwt;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;

public class TokenAuthenticationService implements UserAuthenticationService {
  private Map<String, User> users = new HashMap<>();
  private JWTTokenService tokenService;
  private String issuer = "tom";

  public TokenAuthenticationService(JWTTokenService tokenService) {
    this.tokenService = tokenService;
  }

  private String newToken(final Map<String, String> attributes, final int expiresInSec) {
    Date now = new Date();
    final Claims claims = Jwts
        .claims()
        .setIssuer(issuer)
        .setIssuedAt(now.toDate());

    if (expiresInSec > 0) {
      final DateTime expiresAt = now.plusSeconds(expiresInSec);
      claims.setExpiration(expiresAt.toDate());
    }
    claims.putAll(attributes);

    return Jwts
        .builder()
        .setClaims(claims)
        .signWith(HS256, secretKey)
        .compressWith(COMPRESSION_CODEC)
        .compact();
  }

  @Override
  public Map<String, String> verify(final String token) {
    final JwtParser parser = Jwts
        .parser()
        .requireIssuer(issuer)
        .setClock(this)
        .setAllowedClockSkewSeconds(clockSkewSec)
        .setSigningKey(secretKey);
    return parseClaims(() -> parser.parseClaimsJws(token).getBody());
  }

  @Override
  public Map<String, String> untrusted(final String token) {
    final JwtParser parser = Jwts
        .parser()
        .requireIssuer(issuer)
        .setClock(this)
        .setAllowedClockSkewSeconds(clockSkewSec);

    // See: https://github.com/jwtk/jjwt/issues/135
    final String withoutSignature = substringBeforeLast(token, DOT) + DOT;
    return parseClaims(() -> parser.parseClaimsJwt(withoutSignature).getBody());
  }

  private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
    try {
      final Claims claims = toClaims.get();
      final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
      for (final Map.Entry<String, Object> e: claims.entrySet()) {
        builder.put(e.getKey(), String.valueOf(e.getValue()));
      }
      return builder.build();
    } catch (final IllegalArgumentException | JwtException e) {
      return ImmutableMap.of();
    }
  }

  @Override
  public Date now() {
    final DateTime now = dates.now();
    return now.toDate();
  }

  @Override
  public Optional<User> login(String username, String password) {
    return Optional.empty();
  }
}
