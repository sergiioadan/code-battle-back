    package com.sergioadan.game.infraestructure.config;

    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity

    public class SecurityConfig {

        private final JwtAuthFilter jwtAuthFilter;
        private final UserDetailsService userDetailsService;

        public SecurityConfig(JwtAuthFilter filter, UserDetailsService uds) {
            this.jwtAuthFilter = filter;
            this.userDetailsService = uds;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .headers(headers -> headers
                            .frameOptions(frame -> frame.sameOrigin()) // Permite iframes desde el mismo origen
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/**", "/h2-console/**").permitAll()
                                    .requestMatchers("/search").authenticated() // << Esto permite acceso si el usuario tiene token válido
// <--- PERMITIR
                            .anyRequest().authenticated()
                   )
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authenticationProvider(authProvider())
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // Para H2

            return http.build();
        }


        @Bean
        public AuthenticationProvider authProvider() {
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(userDetailsService);
            auth.setPasswordEncoder(passwordEncoder());
            return auth;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }
    }
