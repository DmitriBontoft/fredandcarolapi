package com.fredandcarol.fredandcarolapi.auth.jwt;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenService implements Clock {
  private static final String DOT = ".";
  private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();
  private String issuer = "dmitri";
  private String secretKey = "secretKey";
  private long clockSkewSec = 300;

  JWTTokenService() {
  }

  public String expiring(Map<String, String> attributes) {
    return newToken(attributes, 600);
  }

  private String newToken(final Map<String, String> attributes, final int expiresInSec) {
    Date now = new Date();
    final Claims claims = Jwts
        .claims()
        .setIssuer(issuer)
        .setIssuedAt(now);

    if (expiresInSec > 0) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(now);
      cal.add(Calendar.SECOND, expiresInSec);
      Date newDate = cal.getTime();
      final Date expiresAt = newDate;
      claims.setExpiration(expiresAt);
    }
    claims.putAll(attributes);

    return Jwts
        .builder()
        .setClaims(claims)
        .signWith(HS256, secretKey)
        .compressWith(COMPRESSION_CODEC)
        .compact();
  }

  public Map<String, String> verify(final String token) {
    final JwtParser parser = Jwts
        .parser()
        .requireIssuer(issuer)
        .setClock(this)
        .setAllowedClockSkewSeconds(clockSkewSec)
        .setSigningKey(secretKey);
    return parseClaims(() -> parser.parseClaimsJws(token).getBody());
  }

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
    return new Date();
  }
}