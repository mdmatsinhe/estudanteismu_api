package siga.artsoft.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/api/login/reset-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/sexo").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/tipo-documento-identificacao").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/nacionalidade").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/provincia").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/faculdade/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/plano-curricular/estudante/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/curso/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/turno").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/delegacao").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/estudante/novo").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/estudante/{id}/iniciar-verificacao-email").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/estudante/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/candidaturas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/distrito/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contacorrente/gerar-taxa-matricula").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contacorrente/gerar-referencia").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/relatorios/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/mpesa/c2b/pay").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/mpesa/callback").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/mpesa/pagar").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/mpesa/c2b/confirmar").permitAll()
                .requestMatchers(HttpMethod.GET, "/actuator/metrics/**").permitAll()
       //         .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("https://estudante.ismu.ac.mz");
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
