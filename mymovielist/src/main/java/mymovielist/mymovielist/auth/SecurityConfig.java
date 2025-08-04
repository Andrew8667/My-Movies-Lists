package mymovielist.mymovielist.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security config defines security features for app
 * @author Andrew Gee
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthentificationFilter jwtAuthentificationFilter; //filter for http requests and responses

    /**
     * Defines the security for http requests
     * @param http allows us to configure security features for web requests
     * @return a security filter chain
     * @throws Exception if error in security feature
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5174"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return http
                .csrf(csrf->csrf.disable()) //csrf is not needed because session is stateless
                .cors(cors -> cors.configurationSource(source)) //grant permission for requests made from port 5173
                .authorizeHttpRequests(
                        authorizeHttp->{
                            authorizeHttp.requestMatchers("/user/login").permitAll();
                            authorizeHttp.requestMatchers("/user/register").permitAll();
                            authorizeHttp.anyRequest().authenticated();
                        }
                ) //authorizes what requests can be sent where
                .addFilterBefore(jwtAuthentificationFilter, BasicAuthenticationFilter.class) //adds the filter before defining where users can send requests
                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }) //sets session as stateless
                .build();
    }
}
