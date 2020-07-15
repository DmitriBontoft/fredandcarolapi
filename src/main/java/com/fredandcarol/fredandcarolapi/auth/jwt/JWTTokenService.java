package com.fredandcarol.fredandcarolapi.auth.jwt;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import java.util.Calendar;
import java.util.Date;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenService implements Clock {
  private static String DOT = ".";
  private static GzipCompressionCodec COMRESSION_CODEC = new GzipCompressionCodec();

  JWTTokenService() {
  }

  public String expiring(ImmutableMap<String, String> attributes) {
    return newToken(attributes, 600);
  }

  private String newToken(ImmutableMap<String, String> attributes, final int expiresInSec) {
    Date now = new Date();
    final Claims claims = Jwts
        .claims()
        .setIssuer("tom")
        .setIssuedAt(now);

    Calendar cal = Calendar.getInstance();
    cal.setTime(now);
    cal.add(Calendar.MINUTE, expiresInSec);
    Date newDate = cal.getTime();

    if (expiresInSec > 0) {
      final Date expiresAt = newDate;
      claims.setExpiration(expiresAt.toDate());
    }
    claims.putAll(attributes);

    return Jwts
        .builder()
        .setClaims(claims)
        .signWith(HS256, "secretkey")
        .compressWith(COMRESSION_CODEC)
        .compact();
  }

  @Override
  public Date now() {
    return null;
  }
}
