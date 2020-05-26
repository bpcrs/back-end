package fpt.capstone.bpcrs.config;

import fpt.capstone.bpcrs.component.JwtAuthenticationEntryPoint;
import fpt.capstone.bpcrs.filter.JwtAuthenticationFilter;
import fpt.capstone.bpcrs.service.impl.CustomUserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static fpt.capstone.bpcrs.constant.RoleEnum.ADMINISTRATOR;
import static fpt.capstone.bpcrs.constant.RoleEnum.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private CustomUserDetailsServiceImpl customUserDetailsService;

  @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);

    // All
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/", "/ws/*", "/ws/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/login", "/google/login", "/register")
        .permitAll();

    // User
    http.authorizeRequests()
        .antMatchers(
            HttpMethod.GET, "/user", "/session/user", "/session/user/*", "/google/authorize")
        .hasRole(USER.getName())
        .antMatchers(HttpMethod.POST, "/rating")
        .hasRole(USER.getName())
        .antMatchers(HttpMethod.PUT, "/session/user/**")
        .hasRole(USER.getName())
        .antMatchers(HttpMethod.PATCH, "/file/*")
        .hasRole(USER.getName())
        .antMatchers(HttpMethod.DELETE, "/userblock/*")
        .hasRole(USER.getName());

    // Administrator
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/account")
        .hasRole(ADMINISTRATOR.getName())
        .antMatchers(HttpMethod.POST, "/category", "/account")
        .hasRole(ADMINISTRATOR.getName())
        .antMatchers(HttpMethod.PUT, "/category/*", "/account/*")
        .hasRole(ADMINISTRATOR.getName())
        .antMatchers(HttpMethod.PATCH, "/account/*")
        .hasRole(ADMINISTRATOR.getName())
        .antMatchers(HttpMethod.DELETE, "/template/*")
        .hasRole(ADMINISTRATOR.getName());

    // Logged
    http.authorizeRequests()
        .antMatchers(
            HttpMethod.GET, "/user")
        .authenticated()
        .antMatchers(HttpMethod.POST, "/file")
        .authenticated()
        .antMatchers(HttpMethod.PUT, "/user", "/file/*")
        .authenticated();

    // Swagger
//    http.authorizeRequests()
//        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
//        .permitAll();

    http.authorizeRequests().anyRequest().denyAll();
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
