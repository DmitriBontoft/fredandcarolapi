package com.fredandcarol.fredandcarolapi.auth.jwt;

import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenService {
  private static String DOT = ".";
  private static GzipCompressionCodec COMRESSION_CODEC = new GzipCompressionCodec();

  JWTTokenService() {
  }
}
