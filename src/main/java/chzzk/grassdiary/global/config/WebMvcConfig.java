package chzzk.grassdiary.global.config;

import chzzk.grassdiary.global.auth.common.AuthMemberResolver;
import chzzk.grassdiary.global.auth.filter.JwtAuthFilter;
import jakarta.servlet.Filter;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
}
