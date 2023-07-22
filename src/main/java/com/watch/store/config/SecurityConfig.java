package com.watch.store.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.watch.store.security.JwtAuthenticationEntryPoint;
import com.watch.store.security.JwtAuthenticationFilter;
import com.watch.store.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
   
	@Autowired
	private UserDetailsService detailsService;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {
		
		http.csrf(cs->cs.disable())
		    .cors(cr->cr.disable())
		    .authorizeHttpRequests(auth->auth
		    .requestMatchers("/auth/login")
		    .permitAll()
		    .requestMatchers(HttpMethod.GET,"/categories/**")
		    .permitAll()
		    .requestMatchers(HttpMethod.GET,"/products/**")
		    .permitAll()
		    .requestMatchers(HttpMethod.POST,"/users")
		    .permitAll()
		    .requestMatchers(HttpMethod.GET,"/users/**")
		    .permitAll()
		    .requestMatchers(HttpMethod.DELETE,"/users/**")
		    .hasRole("ADMIN")
		    .anyRequest()
		    .authenticated())
		    .exceptionHandling(ex->ex
		    .authenticationEntryPoint(authenticationEntryPoint))
		    .sessionManagement(sess->sess
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build() ;		
	}	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(detailsService);
		authenticationProvider.setPasswordEncoder(encoder());
		return authenticationProvider;		
	}	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
