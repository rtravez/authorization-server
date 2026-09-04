package com.rtravez.authorization.server.config;

import java.util.Locale;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InternationalizationConfig implements WebMvcConfigurer {
    private static final Locale DEFAULT_LOCALE = Locale.of("es", "EC");

    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver("locale");
        resolver.setDefaultLocale(DEFAULT_LOCALE);
        resolver.setLanguageTagCompliant(true);
        return resolver;
    }

    @Bean
    FilterRegistrationBean<OncePerRequestFilter> localeFilter(LocaleResolver localeResolver) {
        OncePerRequestFilter filter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(@NonNull jakarta.servlet.http.HttpServletRequest request,
                    @NonNull jakarta.servlet.http.HttpServletResponse response,
                    @NonNull jakarta.servlet.FilterChain filterChain)
                    throws jakarta.servlet.ServletException, java.io.IOException {
                String languageTag = request.getParameter("lang");
                if (languageTag != null) {
                    Locale locale = Locale.forLanguageTag(languageTag);
                    if (locale.equals(Locale.of("es", "EC")) || locale.equals(Locale.US)) {
                        localeResolver.setLocale(request, response, locale);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
        FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @NonNull
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}