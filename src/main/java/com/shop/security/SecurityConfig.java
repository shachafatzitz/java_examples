package com.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Configuration contributes beans to the Spring container.
 * .NET equivalent will be The Program.cs/Startup where you register services in builder.Services.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     * Disables CSRF protection, allows unrestricted access to certain endpoints,
     * requires authentication for all other requests, and enables HTTP Basic authentication.
     * .NET equivalent is configuring middleware in the Program.cs/Startup.cs file using app.UseAuthentication(),
     * app.UseAuthorization(), and defining policies.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/api/products/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /**
     * Defines an in-memory user details service with two users: "alice" and "bob".
     * Both users have the role "USER" and their passwords are stored in plain text.
     * .NET equivalent is configuring in-memory users in the authentication setup in Program.cs/Startup.cs.
     *
     * @return the UserDetailsService managing the in-memory users
     */
    @Bean
    UserDetailsService users() {
        return new InMemoryUserDetailsManager(
            User.withUsername("alice").password("{noop}password").roles("USER").build(),
            User.withUsername("bob").password("{noop}password").roles("USER").build()
        );
    }
}