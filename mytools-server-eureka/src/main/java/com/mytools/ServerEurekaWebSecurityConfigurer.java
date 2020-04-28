package com.mytools;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Secure Eureka server simply by adding Spring Security to pom.xml.
 * About root security auto configuration, Please see my blog: https://www.cnblogs.com/storml/p/10943003.html.
 * <p>
 * Created 2 memory user 'eureka-user' and 'eureka-admin'.
 * 'eureka-user' is used for other eureka clients when connecting this eureka server.
 * 'eureka-admin' is for eureka administrator and it has access to 'actuator' endpoints.
 */
@EnableWebSecurity
public class ServerEurekaWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("eureka-admin").password("{bcrypt}" + encoder.encode("eureka-admin")).roles("ADMIN", "USER")
                .and()
                .withUser("eureka-user").password("{bcrypt}" + encoder.encode("eureka-user")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Why add 'ignoringAntMatchers'
        // See: https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/#securing-the-eureka-server
        http.csrf().ignoringAntMatchers("/eureka/**").and()
                .authorizeRequests()
                .antMatchers("/", "/eureka/**", "/actuator/info", "/actuator/health").hasRole("USER")
                .antMatchers("/actuator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }
}