package com.example.icinbackend.security;

import com.auth0.jwt.JWT;
import com.example.icinbackend.ApiResponse;
import com.example.icinbackend.services.MySQLUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER) // doubt(1)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/health").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs", "/swagger-ui/index.html", "/swagger-ui/**", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/api/balance").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .logout()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    ApiResponse apiRes = new ApiResponse(401, authException.getMessage());
                    ObjectMapper om = new ObjectMapper();
                    OutputStream out = response.getOutputStream();
                    response.setContentType("application/json");
                    om.writeValue(out, apiRes);
                    out.flush();
                })
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return getApplicationContext().getBean(MySQLUserDetailsService.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.setStatus(401);
                Map<String, String> data = new HashMap<>();
                System.out.println("HDKLFJSDFKLJ");

                data.put("error", "hi");

                response.getOutputStream()
                        .println(objectMapper.writeValueAsString(data));
            }
        };
    }
}