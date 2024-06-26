package eventManager.config;


import eventManager.service.MyUserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login").permitAll() // Restrict access to admin endpoints
                        .requestMatchers("/api/v1/events").permitAll()
                        //.requestMatchers("/api/v1/events/save").hasAnyRole("MANAGER")
                        .requestMatchers("/api/v1/events/save").hasAnyAuthority("MANAGER")
                        .requestMatchers( HttpMethod.DELETE,"api/v1/events/{id}").hasAnyRole("MANAGER")
                        .requestMatchers( HttpMethod.GET,"api/v1/events/**").authenticated()
                        //.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                        //.requestMatchers("/api/v1/users/admin").hasRole("ADMIN")   // Restrict access to admin endpoints
                        //.requestMatchers( "api/v1/events").permitAll()
                        //.requestMatchers( HttpMethod.GET,"api/v1/events/**").permitAll()
                        //.requestMatchers( HttpMethod.POST,"api/v1/events/**").hasAnyRole("MANAGER")
                        //.requestMatchers( HttpMethod.DELETE,"api/v1/events/**").hasAnyRole("MANAGER")
                        //.requestMatchers( "api/v1/events/save").hasAnyRole("MANAGER")
                        //.requestMatchers("api/v1/users", "api/v1/users/**").permitAll()       // Allow access to registration only for unauthenticated users
                        //.requestMatchers("api/v1/registrations", "api/v1/registrations/**").hasAnyRole("MANAGER")
                )
                //.formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // Allow unrestricted access to the login page
                //.logout(LogoutConfigurer::permitAll) // Allow unrestricted access to the logout endpoint
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource () {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList(
                        "http://localhost:8080",
                        "http://localhost:5173",
                        "http://localhost:4200",
                        "http://localhost:3000"
                ));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);
                return config;
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

