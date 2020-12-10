package by.grsu.coursework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // отключает защиту csfr, чтобы можно было создавать посты для блога (иначе будет вызываться ошибка 404)

        http
                .authorizeRequests()
                    .antMatchers("/", "/home", "/error", "/blog", "/about").permitAll()
                    .antMatchers("/blog/add", "/blog/{id}/edit").authenticated()
                    //.anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    // 'withDefaultPasswordEncoder()' is deprecated because:
    // Using this method is not considered safe for production, but is acceptable for demos and getting started.
    // For production purposes, ensure the password is encoded externally.

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager (
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("ADMIN")
                        .build()
        );
    }
}