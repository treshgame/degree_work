package dev.university.degree.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public WebSecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/css/**", "/js/**", "/bootstrap.min.css").permitAll()
                            .requestMatchers("/owner/**", "/owner").hasAuthority("OWNER")
                            .requestMatchers("/vet/**", "/vet").hasAnyAuthority("VET", "OWNER")
                            .requestMatchers("/inpatient/**", "/inpatient").hasAnyAuthority("INPATIENT", "OWNER")
                            .requestMatchers("/administrator/**","/administrator").hasAnyAuthority("ADMINISTRATOR", "OWNER")
                            .anyRequest().authenticated()
                ).formLogin(
                    form -> form.successHandler(customAuthenticationSuccessHandler).permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").permitAll());
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
