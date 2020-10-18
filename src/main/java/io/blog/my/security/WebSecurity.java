package io.blog.my.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

import static org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY;

@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private final AccessDeniedHandler accessDeniedHandler;
	private final DataSource dataSource;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(AccessDeniedHandler accessDeniedHandler,
	                   DataSource dataSource,
	                   BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.accessDeniedHandler = accessDeniedHandler;
		this.dataSource = dataSource;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/home", "/sign-up/**", "/error", "/blog/**", "/post/**").permitAll()
				.antMatchers("/newPost/**", "/commentPost/**", "/createComment/**").hasAnyRole("USER")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/sign-in")
				.defaultSuccessUrl("/home")
				.permitAll()
				.and()
				.logout()
				.permitAll()
				.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.and().headers().frameOptions().disable();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		final String rolesQuery = "select u.username, r.role from users as u " +
				"inner join user_role as ur on(u.id = ur.user_id) " +
				"inner join roles as r on (ur.role_id = r.id) where u.username = ?";
		auth.jdbcAuthentication()
				.usersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	
}



