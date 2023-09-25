package WooJJam.backstudy.utils;

import WooJJam.backstudy.repository.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {


    public static String createAccessToken(String email, String secretKey, long expiredMs) {
        Claims claims = Jwts.claims().setSubject("AccessToekn"); // JWT payload 에 저장되는 정보단위
        claims.put("email", email); // 정보는 key / value 쌍으로 저장된다.
//        claims.put("UUID", UUID.randomUUID().toString());
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더의 타입 지정
                .setClaims(claims)
                .setIssuer("WooJJam") // 토큰 발급자
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 해싱 알고리즘, 시크릿 키
                .compact(); // JWT 토큰 생성
    }

    public static String createRefreshToken(String email, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims().setSubject("RefreshToken");
        claims.put("email",email);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static boolean validateToken(String token, String secretKey) {
        System.out.println("parseClaims(token, secretKey) = " + parseClaims(token, secretKey));
        System.out.println("parseClaims(token, secretKey).getExpiration() = " + parseClaims(token, secretKey).getExpiration());
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token",e);
        }catch (ExpiredJwtException e) {
            log.info("Expired JWT Token",e);
        }catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token",e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }

    public static String getUserInfo(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().get("email", String.class);
    }


    public static Claims parseClaims(String token, String secretKey) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static void setAuthentication(HttpServletRequest request, String email) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
