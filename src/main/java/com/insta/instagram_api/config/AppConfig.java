package com.insta.instagram_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// 메서드 체이닝 지양, 람다식으로 함수형 설정 권고.
@Configuration
public class AppConfig {
    // TODO: securityConfiguration 함수 고치기. 체인형 함수 지양, 더 이상 지원하지 않는 패키지.
    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(null, null)
                .addFilterBefore(null, null)
                .csrf().disable()
                .formLogin().and().httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
