package spring.boot.library.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Disable CSRF
        http.csrf(csrf -> csrf.disable());

        //Protect endpoint /api/<type>/secure
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/api/books/secure/**", "/api/reviews/secure/**",
                                "/api/messages/secure/**", "/api/admin/secure/**").authenticated()
                        .requestMatchers("/api/books/**", "/api/reviews/**", "/api/histories/**",
                                "/api/messages/**","/error").permitAll())
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())));

        //Add cors
        http.cors(cors -> cors.configure(http));

        //Add content negotiation
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401's to make the response body friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Ensure this matches your JWT claims

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return authenticationConverter;
    }



}
