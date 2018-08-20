package com.tentative.security.config;

import com.tentative.security.interceptor.TokenInterceptor;
import com.tentative.security.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册Interceptor
 *
 * @author Shinobu
 * @since 2018/2/28
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    private final UserInterceptor userInterceptor;

    @Value("${customized.url-white-list}")
    private String urlWhiteList;

    @Autowired
    public InterceptorConfig(TokenInterceptor tokenInterceptor, UserInterceptor userInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.userInterceptor = userInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns(urlWhiteList.split(","));
        registry.addInterceptor(userInterceptor).addPathPatterns("/**").excludePathPatterns(urlWhiteList.split(","));
    }

}