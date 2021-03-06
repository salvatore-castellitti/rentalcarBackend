package com.rentalcar.rentalcarbackend.config;

import com.rentalcar.rentalcarbackend.jwt.JwtAuthorizationFilter;
import com.rentalcar.rentalcarbackend.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //cross origin resource sharing: localhost:8080. localhost:4200
        http.cors().and()
                .authorizeRequests()
                //these are public
                .antMatchers("/resources/**","/error").permitAll()
                .antMatchers("/api/v1/users/login").permitAll()
                .antMatchers("/api/v1/users/registration/").permitAll()
                .antMatchers("/api/v1/users/list").hasRole("ADMIN")
                .antMatchers("/api/v1/users/add").hasRole("ADMIN")
                .antMatchers("/api/v1/users/update").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/users/get/**").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/users/delete/**").hasRole("ADMIN")
                .antMatchers("/api/v1/vehicles/list").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/vehicles/add").hasRole("ADMIN")
                .antMatchers("/api/v1/vehicles/get/**").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/vehicles/delete/**").hasRole("ADMIN")
                .antMatchers("/api/v1/vehicles/freeVehicle/**").hasRole("CUSTOMER")
                .antMatchers("/api/v1/vehicles/**").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/reservations/list").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/reservations/add").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/reservations/get/**").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/api/v1/reservations/delete/**").hasAnyRole("ADMIN","CUSTOMER")
                .anyRequest().fullyAuthenticated()
                .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/user/logout","POST"))
                .and()
                .formLogin().loginPage("/api/v1/users/login").and()
                .httpBasic().and()
                .csrf().disable();

        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }


}
