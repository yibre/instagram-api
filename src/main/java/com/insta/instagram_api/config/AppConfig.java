package com.insta.instagram_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


// 메서드 체이닝 지양, 람다식으로 함수형 설정 권고.
@Configuration
@EnableWebSecurity
public class AppConfig {
        @Bean
        public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {
            http
                    // 1. Session Management: Configure session management to be stateless
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    // 2. Authorization Rules: Define authorization rules for HTTP requests
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(HttpMethod.POST, "/signup").permitAll() // Allow POST requests to /signup for everyone
                            .anyRequest().authenticated() // All other requests require authentication
                    )
                    // 3. CSRF Protection: Disable CSRF protection (common for stateless APIs)
                    .csrf(csrf -> csrf.disable())
                    // 4. Form Login: Enable form login with default settings
                    .formLogin(Customizer.withDefaults()) // Or configure it: .formLogin(form -> form.loginPage("/login").permitAll())
                    // 5. HTTP Basic Authentication: Enable HTTP Basic authentication with default settings
                    .httpBasic(Customizer.withDefaults()) // Or configure it if needed
                    // 커스터마이징 필터
                    .addFilterBefore(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                    .addFilterAfter(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class);

            // 6. Custom Filters:
            // The original code had .addFilterAfter(null, null) and .addFilterBefore(null, null).
            // Using 'null' for the filter instance is not valid.
            // If you have custom filters, you should add them like this:
            // http.addFilterBefore(yourCustomFilter, UsernamePasswordAuthenticationFilter.class);
            // http.addFilterAfter(anotherCustomFilter, YourOtherFilter.class);
            //
            // For example, if 'jwtAuthenticationFilter' is a bean:
            // http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            //
            // If you don't have custom filters to add here, these lines should be removed.
            // I've removed them in this updated version. If you intended to add filters,
            // please provide the filter classes and where they should be placed in the chain.

            // 커스터마이징 필터 추가: 5th lecture 37:10
            return http.build();
        }

        // Example of how you might define a filter bean if you were using one:
        // @Bean
        // public YourCustomFilter jwtAuthenticationFilter() {
        //     return new YourCustomFilter();
        // }

    // securityConfiguration 함수 고치기. 체인형 함수 지양, 더 이상 지원하지 않는 패키지.
        /* 강의 오리지널 함수
    @Bean
    public SecurityFilterChain Test_securityConfiguration(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidationFilter(),  BasicAuthenticationFilter.class)
                .csrf().disable()
                .formLogin().and().httpBasic();

        return http.build();
    }
         */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
