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
                // 1. Session Management: Replaced the deprecated chain with the new lambda style.
                // This configures the application to be stateless, which is typical for REST APIs using JWT.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 2. Authorization Rules: Defined using the lambda DSL.
                .authorizeHttpRequests(authorize -> authorize
                        // Allow POST requests to "/signup" without authentication.
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        // All other requests must be authenticated.
                        .anyRequest().authenticated()
                )

                // 3. Custom Filters: Your JWT filters are added to the security chain.
                // This is the correct way to add filters in the new configuration style.
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)

                // 4. CSRF Protection: Disabled using the new lambda style.
                // This is common for stateless APIs where browser-based session cookies are not used.
                .csrf(csrf -> csrf.disable())

                // 5. Authentication Methods: Kept formLogin and httpBasic as in the original code.
                // Customizer.withDefaults() enables them with their default settings.
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        // Build the SecurityFilterChain object.
        return http.build();
    }

    // securityConfiguration 함수 고치기. 체인형 함수 지양, 더 이상 지원하지 않는 패키지.
    /*
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
