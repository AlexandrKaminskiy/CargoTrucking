package by.singularity.security;

import by.singularity.security.filter.AppAuthenticationFilter;
import by.singularity.security.filter.AppAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppAuthorizationFilter appAuthorizationFilter;
    @Value("${jwt.expiration.access}")
    private Integer accessExpires;
    @Value("${jwt.expiration.refresh}")
    private Integer refreshExpires;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AppAuthenticationFilter appAuthenticationFilter
                = new AppAuthenticationFilter(authenticationManagerBean(), secret, accessExpires, refreshExpires);
        appAuthenticationFilter.setFilterProcessesUrl("/api/sign-in");
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/sign-in/**","/api/refresh/**","/api/about/**").permitAll();
        http.authorizeRequests().antMatchers("/api/logout/**").authenticated();
        http.authorizeRequests().antMatchers("/api/user/**","/api/clients/**").hasAuthority("SYS_ADMIN");
        http.authorizeRequests().antMatchers("/api/storages/**","/api/product-owners/**","/api/cars/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/invoices/","/api/invoices/{id}","/api/product-writeoffs/**").hasAuthority("DISPATCHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/waybills/**").hasAuthority("DISPATCHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/waybills/checkpoints/{checkpointId}").hasAuthority("DRIVER");
        http.authorizeRequests().antMatchers("/api/waybills/**","/api/invoices/validate/{number}").hasAuthority("MANAGER");
        http.addFilter(appAuthenticationFilter);
        http.addFilterBefore(appAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}