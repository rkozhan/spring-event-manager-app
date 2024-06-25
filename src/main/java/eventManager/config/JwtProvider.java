package eventManager.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor((JwtConstant.SECRET_KEY.getBytes()));
    public static String generateToken(Authentication auth) {
        Collection <? extends GrantedAuthority> authorities = auth.getAuthorities();

        return Jwts.builder().setIssuedAt(new Date())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JwtConstant.EXPIRATION_TIME))
                .claim("email", auth.getName())
                .claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken (String jwt) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }
}
