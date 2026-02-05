package com.yedam.app.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.yedam.app.security.filter.JWTCheckFilter;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
//@EnableMethodSecurity
public class CustomSecurityConfig {


  private JWTCheckFilter jwtCheckFilter;

  @Autowired
  private void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
    this.jwtCheckFilter = jwtCheckFilter;
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    log.info("filter chain............");

    httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> {
      httpSecurityFormLoginConfigurer.disable();

    });

    httpSecurity.logout( config -> config.disable());

    httpSecurity.csrf(config -> { config.disable();});

    httpSecurity.sessionManagement(sessionManagementConfigurer -> {
      sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
    });

    httpSecurity.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

    httpSecurity.cors(cors -> {
      cors.configurationSource(corsConfigurationSource());
    });

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.setAllowedOriginPatterns(List.of("*")); // 어디서든 허락
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

}