package com.wol.mock.hplink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class MockHplinkApplication {

	public static final String GLOBAL_CONFIG_PROP = "com.wol.mock.hplink.config";
	public static final String LOGGING_CONFIG_PROP = "com.wol.mock.hplink.logging.config";
	public static final String HTTP_SERVER_PORT_PROP = "com.wol.mock.hplink.server.port";
	public static final String HTTP_SERVER_DOC_ROOT_PROP = "com.wol.mock.hplink.server.root";
	public static final String HTTP_SERVER_KEYSTORE_PROP = "com.wol.mock.hplink.server.keystore";
	public static final String HTTP_SERVER_KEYSTORE_PASS_PROP = "com.wol.mock.hplink.server.keystore.pass";
	public static final String H2_JDBC_URL_PROP = "com.wol.mock.hplink.h2.jdbc.url";
	public static final String INTERNAL_LOGGING_PATTERN = "%d [%p|%c|%C{1}] %m%n";
	
	@Configuration
	@EnableWebSecurity
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("user").password("pwd").roles("USER");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();//disable CSRF
			http.headers().frameOptions().disable();//disbale frame options in order for DB console to function correctly.
			http.authorizeRequests().antMatchers("**").permitAll();//disable internal security implmentation
		}
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MockHplinkApplication.class, args);
	}
}
