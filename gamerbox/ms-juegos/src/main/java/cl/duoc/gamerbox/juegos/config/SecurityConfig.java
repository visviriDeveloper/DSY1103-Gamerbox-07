package cl.duoc.gamerbox.juegos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Cualquiera puede ver el catálogo de juegos
                        .requestMatchers(HttpMethod.GET, "/api/v1/juegos/**").permitAll()

                        // Solo el ADMIN puede hacer POST, PUT o DELETE en juegos
                        .requestMatchers("/api/v1/juegos/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}1234")
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}1234")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}