package com.mmeftauddin.journalapp.config;

import com.mmeftauddin.journalapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    //private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        //this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health-check/**", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user", "/user/").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/user", "/user/").authenticated()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/error", "/error/**").permitAll()
                        .requestMatchers("/v2/journal/**").authenticated()
                        .anyRequest().denyAll()
                )
                .httpBasic(withDefaults())
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                // If you’re using sessions + POST, CSRF can bite you. For APIs, ignore /user too:
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/user/**"));

        return http.build();
    }
    /*public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated());
        //http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.headers((headers) ->
                headers.frameOptions(frameOptions ->
                        frameOptions.sameOrigin()));
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }*/


    /*@Bean
    UserDetailsService userDetailsService() {
        UserDetails  user = User.withUsername("user1")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        UserDetails  admin = User.withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.createUser(admin);
        manager.createUser(user);
        return manager;
        //return new InMemoryUserDetailsManager(user, admin);
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
