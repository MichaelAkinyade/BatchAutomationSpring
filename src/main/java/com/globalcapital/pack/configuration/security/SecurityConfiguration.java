package com.globalcapital.pack.configuration.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * @Autowired private DataSource dataSource;
	 */

	@Value("${cron.batch.RegularServiceBatchRunTwo}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	@Primary
	public DataSource dataSource() {
	    return DataSourceBuilder
	        .create()
	        .username("sa")
	        .password("")
	        .url("jdbc:h2:mem:AZ;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
	        .driverClassName("org.h2.Driver")
	        .build();
	}

	//original login config
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.jdbcAuthentication().
	 * usersByUsernameQuery("select u.username,u.password, r.id from user u inner join role r on (r.id=u.role_id) where u.username=?"
	 * )//.authoritiesByUsernameQuery(rolesQuery)
	 * .dataSource(dataSource()).passwordEncoder(bCryptPasswordEncoder); }
	 */
	
	@Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/resources/**", "/static/**", "/css/**","/fonts/**", "/templates/**","/images/**", "/js/**", "/vendor/**","/templates/**", "/login/**", "/", "/admin/**").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/registration", "/h2/**").permitAll()
				.antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
				.authenticated().and().csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/admin/home", true).and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
				.exceptionHandling().accessDeniedPage("/access-denied");
		http.headers().frameOptions().disable();
	}

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**",
	 * "/js/**", "/images/**", "/template/**", "/admin/**", "/login/**"); }
	 */

}
