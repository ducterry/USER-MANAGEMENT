package com.ndangducbn.ducterrybase.config;

import com.ndangducbn.ducterrybase.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] SWAGGER_DOCUMENT = {"/swagger-ui.html",
            "/v2/api-docs",
            "/swagger-resources/configuration/ui",
            "/configuration/security",
            "/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/webjars/**",
            "/actuator/**"};
    public static final String[] ACCOUNT_USER = {"/api/users/account/v1/signup", "/api/users/account/v1/signin"};
    public static final String HEALTH_CHECK = "/healthcheck";
    public static final String API = "/api/**";


    private final AuthorizationFilter authorizationFilter;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public WebSecurityConfig(AuthorizationFilter authorizationFilter, AuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.authorizationFilter = authorizationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;

    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(SWAGGER_DOCUMENT).permitAll()
                .antMatchers(HttpMethod.POST,ACCOUNT_USER).permitAll() /*Api đăng nhập đăng kí không cần kiểm tra xác thực */
                .antMatchers(HttpMethod.GET, HEALTH_CHECK).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Không sử dụng session lưu lại trạng thái của principal
                .and()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
