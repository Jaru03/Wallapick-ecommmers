package Wallapick.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**", "/product/**", "/order/**", "/api/ebay/**", "/api/stripe").permitAll()  // Allow without authentication
                        .anyRequest().authenticated()  // The rest requires authentication
                )
                .httpBasic(Customizer.withDefaults()); // Other authentication options

        return http.build();
    }
}