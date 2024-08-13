package xyz.mariosm.bank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/accounts/**").permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
