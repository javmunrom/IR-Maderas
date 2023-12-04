package org.springframework.samples.maderas.configuration;

import static org.springframework.security.config.Customizer.withDefaults;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.maderas.configuration.jwt.AuthEntryPointJwt;
import org.springframework.samples.maderas.configuration.jwt.AuthTokenFilter;
import org.springframework.samples.maderas.configuration.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	DataSource dataSource;

	private static final String ADMIN = "ADMIN";
	

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		
		http
			.cors(withDefaults())		
			.csrf(AbstractHttpConfigurer::disable)		
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))			
			.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()))
			.exceptionHandling((exepciontHandling) -> exepciontHandling.authenticationEntryPoint(unauthorizedHandler))			

			.authorizeHttpRequests(authorizeRequests ->	authorizeRequests
			.requestMatchers(
                PathRequest.toStaticResources().atCommonLocations(), 
                PathRequest.toH2Console()
            ).permitAll()
			.requestMatchers("/","/resources/**", "/webjars/**", "/static/**", "/swagger-resources/**").permitAll()
			.requestMatchers( "/api/v1/games/**","/api/v1/players/**","/api/v1/boards","/", "/oups","/api/v1/auth/**","/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**", "/api/v1/**").permitAll()	
			.requestMatchers("/api/v1/users","api/v1/players/combinations", "/api/v1/auth", "/api/v1/players/addCombination", "/api/v1/players/**", "api/v1/combination/**", "/h2-console", "/h2-console/**").permitAll()											
			.requestMatchers("/api/v1/combination/**", "api/v1/players/addCombination", "api/v1/players/combinations").permitAll()
			.requestMatchers("/api/v1/clinicOwners/all").hasAuthority(ADMIN)
			.requestMatchers(HttpMethod.DELETE, "/api/v1/consultations/**").hasAuthority(ADMIN)
			.requestMatchers("/api/v1/owners/*/pets/**").authenticated()				
			.requestMatchers("/api/v1/owners/**").hasAuthority(ADMIN)
			.requestMatchers("/api/v1/visits/**").authenticated()
			.requestMatchers(HttpMethod.GET, "/api/v1/pets/stats").hasAuthority(ADMIN)
			.requestMatchers("/api/v1/pets").authenticated()
			.requestMatchers("/api/v1/pets/**").authenticated()
			.requestMatchers(HttpMethod.GET, "/api/v1/vets/stats").hasAuthority(ADMIN)
			.requestMatchers(HttpMethod.GET, "/api/v1/vets/**").authenticated()
			.anyRequest().authenticated())					
			
			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);		
		return http.build();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}	


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
}