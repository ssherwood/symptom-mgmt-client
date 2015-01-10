package org.coursera.androidcapstone.symptom;

import org.coursera.androidcapstone.symptom.domain.Doctor;
import org.coursera.androidcapstone.symptom.domain.Medicine;
import org.coursera.androidcapstone.symptom.domain.Patient;
import org.coursera.androidcapstone.symptom.domain.PatientAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class Application extends RepositoryRestMvcConfiguration {

	private static final String SYMPTOM_MANAGEMENT_RESOURCE_ID = "symptom-management";
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Doctor.class, Patient.class, Medicine.class, PatientAlert.class);
    }
	
	/**
	 * Re-evaluate this approach with Spring-OAuth 2.0.4 (see http://stackoverflow.com/questions/26621693/how-to-get-spring-boot-and-oauth2-example-to-use-password-grant-credentials-othe)
	 * 
	 */
	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
			authManagerBuilder
				.inMemoryAuthentication()
					.withUser("user1").password("password1").roles("USER").and()
					.withUser("patient1").password("password1").roles("USER", "PATIENT").and()
					.withUser("patient2").password("password2").roles("USER", "PATIENT").and()
					.withUser("patient3").password("password3").roles("USER", "PATIENT").and()
					.withUser("patient4").password("password3").roles("USER", "PATIENT").and()
					.withUser("doctor1").password("password1").roles("USER", "DOCTOR").and()
					.withUser("doctor2").password("password2").roles("USER", "DOCTOR").and()
					.withUser("admin1").password("password1").roles("ADMIN");
		}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
	}

	/**
	 * 
	 */
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(SYMPTOM_MANAGEMENT_RESOURCE_ID);
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.csrf().disable();
			
			//.requiresChannel().anyRequest().requiresSecure().and() // REQUIRE HTTPS - this breaks on CloudFoundry
			
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
			 
			http.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')");
			// @formatter:on
		}
	}

	/**
	 * 
	 */
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients.inMemory()
				.withClient("android-symptoms-management")
					.authorizedGrantTypes("password", "refresh_token")
					.authorities("ROLE_CLIENT")
					.scopes("read", "write")
					.resourceIds(SYMPTOM_MANAGEMENT_RESOURCE_ID)
					.secret("secret");  // changeme
			// @formatter:on
		}
	}
}