package org.bfsi.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTResponse {
    String username;
    String token;
    String refreshToken;
}
