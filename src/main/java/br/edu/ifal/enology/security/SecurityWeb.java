package br.edu.ifal.enology.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import br.edu.ifal.enology.service.LoginDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityWeb extends WebSecurityConfigurerAdapter{

    @Autowired
    LoginDetailsService UserDetails;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/mapa", "/perfil", "/editarPerfil", "/licao")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .usernameParameter("email")
            .passwordParameter("senha")
            .defaultSuccessUrl("/mapa")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login"); 
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.userDetailsService(UserDetails)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    public void configure(WebSecurity web) throws Exception{

        web.ignoring().antMatchers("/h2-console/**", "/salvar/**", "/cadastrar/**", "/buscar/**");
    }
}