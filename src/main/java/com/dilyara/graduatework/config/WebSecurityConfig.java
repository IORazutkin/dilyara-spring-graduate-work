package com.dilyara.graduatework.config;

import com.dilyara.graduatework.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
  private final AuthService authService;

  @Bean
  public PasswordEncoder getPasswordEncoder () {
    return new BCryptPasswordEncoder(8);
  }

  @Override
  protected void configure (HttpSecurity http) throws Exception {
    http
      .csrf().disable()
        .cors()
        .and()
      .authorizeRequests()
//        .anyRequest().permitAll()
        .mvcMatchers("/oauth2/**").permitAll()
        .mvcMatchers(HttpMethod.POST, "/api/user").permitAll()
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/api/user", true)
        .failureForwardUrl("/login-error")
        .failureUrl("/login-error")
        .permitAll()
        .and()
      .logout()
        .permitAll()
        .and()
      .oauth2Login()
        .loginPage("/oauth2")
        .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authService)
      .passwordEncoder(getPasswordEncoder());
  }


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:3000", "http://localhost:8000")
      .allowedMethods("*")
      .allowCredentials(true);
  }
}
