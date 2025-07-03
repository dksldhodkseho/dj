// 파일 위치: user/src/main/java/miniproject/config/SecurityConfig.java
package miniproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // Spring Security 기능을 활성화합니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * PasswordEncoder를 스프링의 Bean으로 등록합니다.
     * BCryptPasswordEncoder는 강력한 해시 함수 중 하나입니다.
     * @return PasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP 보안 설정을 구성합니다.
     * 우선 모든 요청에 대해 접근을 허용하도록 설정합니다.
     * (나중에 JWT 필터 등을 적용할 때 이 부분을 더 정교하게 수정할 수 있습니다.)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()       // CORS 비활성화
            .csrf().disable()       // CSRF 보안 비활성화
            .formLogin().disable()  // 기본 폼 로그인 비활성화
            .headers().frameOptions().disable();
    }
}