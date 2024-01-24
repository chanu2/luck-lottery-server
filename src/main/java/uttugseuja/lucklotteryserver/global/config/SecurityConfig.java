package uttugseuja.lucklotteryserver.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uttugseuja.lucklotteryserver.global.security.JwtTokenFilter;
import uttugseuja.lucklotteryserver.global.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
//                .exceptionHandling((handling) ->
//                        handling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((registry) ->
                        registry.requestMatchers("/api/hello").permitAll()
                                .requestMatchers("/api/authentication").permitAll()
                                .requestMatchers("/api/signup").permitAll()
                                .anyRequest().authenticated()
                );

//        .authorizeHttpRequests(request -> request
//                .requestMatchers(
//                        "/test"
//                ).permitAll()
//        )
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated());

        return http.build();
    }

}
