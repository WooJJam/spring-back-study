package WooJJam.backstudy.config;

import WooJJam.backstudy.utils.JwtUtil;
import WooJJam.backstudy.week2.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization: {}",authorization);

        // Token 없으면 block
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authentication 이 없습니다");
            filterChain.doFilter(request,response);
            return;
        }

        // Token 꺼내기
        String accessToken = authorization.split(" ")[1];
        String refreshToken = request.getHeader("RefreshToken");

        System.out.println("refreshToken = " + refreshToken);
        String email= JwtUtil.getUserInfo(accessToken, secretKey);
        System.out.println("email = " + email);
         //Token Expired 되었는지 여부
        if(JwtUtil.validateToken(accessToken, secretKey)) {
            JwtUtil.setAuthentication(request, email);
            filterChain.doFilter(request, response);
            System.out.println("만료된 토큰이 아님");
        }else {
            System.out.println(" 만료된 토큰임" );
            filterChain.doFilter(request,response);
        }
    }

}
