package com.cafe.security;

import com.cafe.service.core.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.POST, "/users/register").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.POST, "/products/register").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.PUT, "/products/update/*").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.POST, "/tables/register").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.GET, "/tables-to-waiters").hasAuthority("WAITER")
                .antMatchers(HttpMethod.POST, "/tables-to-waiters/assign").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.POST, "/orders/register/*").hasAuthority("WAITER")
                .antMatchers(HttpMethod.POST, "/products-in-order/register/*").hasAuthority("WAITER")
                .antMatchers(HttpMethod.PUT, "/products-in-order/update/*").hasAuthority("WAITER")
                .antMatchers(HttpMethod.PUT, "/orders/update/*").hasAuthority("WAITER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}


