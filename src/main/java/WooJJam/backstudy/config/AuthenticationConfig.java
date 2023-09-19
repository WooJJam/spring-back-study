package WooJJam.backstudy.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/spring-security/v1/users/login").permitAll() //login은 항상 허용
                                .requestMatchers(HttpMethod.POST,"/spring-security/v1/reviews").authenticated()
                                // review는 여전히 인증 필요
                        );

//                .authorizeHttpRequests(request -> request
//                                .requestMatchers("/spring-security/v1/reviews").authenticated()
//                                .requestMatchers(HttpMethod.POST, "/spring-security/v1/login").permitAll()
////                                .dispatcherTypeMatchers(HttpMethod.valueOf("/spring-security/**")).permitAll()
//                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                                .anyRequest().authenticated()    // 어떠한 요청이라도 인증필요
//                )
//                .formLogin(login -> login    // form 방식 로그인 사용
//                        .defaultSuccessUrl("/view/dashboard", true)    // 성공 시 dashboard로
//                        .permitAll()    // 대시보드 이동이 막히면 안되므로 얘는 허용
//                ).logout(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
