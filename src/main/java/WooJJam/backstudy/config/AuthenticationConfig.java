package WooJJam.backstudy.config;

import WooJJam.backstudy.utils.JwtUtil;
import WooJJam.backstudy.week2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secretKey;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/week2/v1/register").permitAll() //login은 항상 허용
                                .requestMatchers("/spring-security/v1/users/login").permitAll() //login은 항상 허용
                                .requestMatchers("/spring-security/v1/reviews").authenticated()
                                // review는 여전히 인증 필요
                        )
                .addFilterBefore(new JwtFilter(jwtUtil, userService, secretKey), UsernamePasswordAuthenticationFilter.class);

//                .formLogin(login -> login    // form 방식 로그인 사용
//                        .defaultSuccessUrl("/view/dashboard", true)    // 성공 시 dashboard로
//                        .permitAll()    // 대시보드 이동이 막히면 안되므로 얘는 허용
//                ).logout(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
