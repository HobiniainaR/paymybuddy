package com.hobiniaina.paymybuddy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT email , 'user' FROM user WHERE email = ?")
                .usersByUsernameQuery("SELECT email , password , true FROM user WHERE email = ?");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry.requestMatchers("/register").permitAll()
                                .anyRequest().authenticated())
                .formLogin(
                        login -> login.loginPage("/login")
                                .usernameParameter("username"                                                      )
                                .passwordParameter("password")
                                .defaultSuccessUrl("/transfers/transfer")
                                .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }
}
