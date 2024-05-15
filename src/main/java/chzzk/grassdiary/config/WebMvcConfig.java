package chzzk.grassdiary.config;

import chzzk.grassdiary.auth.common.AuthMemberResolver;
import chzzk.grassdiary.auth.exception.AuthenticationException;
import chzzk.grassdiary.auth.filter.JwtAuthFilter;
import chzzk.grassdiary.auth.jwt.JwtTokenExtractor;
import chzzk.grassdiary.auth.jwt.JwtTokenProvider;
import chzzk.grassdiary.domain.member.Member;
import chzzk.grassdiary.domain.member.MemberRepository;
import com.amazonaws.HttpMethod;
import jakarta.servlet.Filter;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthMemberResolver authMemberResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authMemberResolver);
    }

    @Bean
    public FilterRegistrationBean<Filter> authFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtAuthFilter);
        registration.setOrder(1);
        registration.addUrlPatterns(
                "/api/example",
                "/api/test",
                "/api/diary/*",
                "/api/grass/*",
                "/api/main/*",
                "/api/member/*",
                "/api/search/*",
                "/api/shared/diaries/*",
                "/api/members/me",
                "/api/me"
        );
        return registration;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
