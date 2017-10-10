/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS;

import br.com.jgeniselli.catalogacaoWS.model.security.AuthErrorHandler;
import br.com.jgeniselli.catalogacaoWS.model.security.AuthSuccessHandler;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author jgeniselli
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private AuthErrorHandler errHandler;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Value("${spring.queries.users-query}")
    private String usersQuery;
	
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;
    
    @Autowired
    private AuthSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/web/register.html").hasRole("ADMIN")
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/web/login.html")
                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/web/home.html")
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/theme/**", "/web/mockReport", "/api/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)	
                .passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery);
    }
    
    
}
