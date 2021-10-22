package com.weblab.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.weblab.app.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(100)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("usuarioServicio")
	public UsuarioServicio usuarioServicio;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/", "/js/", "/img/", "/*").permitAll().and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login/logincheck").usernameParameter("dni")
				.passwordParameter("clave").defaultSuccessUrl("/login/inicio").failureUrl("/login?error=error")
				.permitAll().and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll().and().csrf()
				.disable();
	}
}
