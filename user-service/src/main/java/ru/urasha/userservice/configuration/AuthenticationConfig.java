package ru.urasha.userservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.urasha.JwtTokenUtil;
import ru.urasha.userservice.filters.JwtAuthenticationFilter;
import ru.urasha.userservice.repositories.UserAccountRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    private final UserAccountRepository userAccountRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationConfig(UserAccountRepository userAccountRepository, JwtTokenUtil jwtTokenUtil) {
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return username -> userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) {
        builder.eraseCredentials(false);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://frontend:80"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
