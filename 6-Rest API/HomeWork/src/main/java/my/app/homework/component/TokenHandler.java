package my.app.homework.component;

import org.springframework.stereotype.Component;

@Component
public class TokenHandler {

    private final String TOKEN = "token";

    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public boolean isValidToken(String token) {
        return TOKEN.equals(this.extractTokenFromHeader(token));
    }
}
