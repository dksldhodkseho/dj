package miniproject.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final Environment env;

    @Autowired
    public JwtTokenProvider(Environment env) {
        this.env = env;
    }

    // JWT 생성
    public String createToken(String userId, String role) {
        long tokenValidTime = 1000L * 60 * 60; // 토큰 유효시간: 1시간

        return Jwts.builder()
            .setSubject(userId) // 토큰의 주체 (누구의 토큰인지)
            .claim("role", role) // 비공개 클레임. 역할 정보 추가
            .setIssuedAt(new Date()) // 토큰 발행 시간
            .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime)) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS256, env.getProperty("token.secret")) // 비밀키로 서명
            .compact();
    }
}