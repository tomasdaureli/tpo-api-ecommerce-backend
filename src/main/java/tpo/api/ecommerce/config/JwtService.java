package tpo.api.ecommerce.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties properties;

    private SecretKey secretKey;

    @PostConstruct
    public void initialize() {
        this.secretKey = createSecretKey(properties.getSecretKeyString());
    }

    private SecretKey createSecretKey(String secretKeyString) {
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String generateToken(String userId, String email) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .setIssuedAt(new Date())
                .signWith(secretKey)
                .compact();
    }

}
