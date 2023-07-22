package com.watch.store.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.watch.store.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private Logger logger=LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private UserDetailsService detailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Authorization
		//requestHeader example = Bearer 25652040jgjrhgir
		//So first we check if requestHeader start with Bearer or not if starts then we will  extract the token  
		String requestHeader = request.getHeader("Authorization");
		String userName=null;
		String token=null;
		if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
			    token =  requestHeader.substring(7);
			    try {
			    	
			    userName = this.helper.getUsernameFromToken(token);
			    	
			    }catch(IllegalArgumentException ex){
			    	logger.info("Illegal argument while fetching the UseName");
			    	ex.printStackTrace();
			    }catch (ExpiredJwtException ex) {
			    	logger.info("Given Jwt token is expired");
			    	ex.printStackTrace();
				}catch (MalformedJwtException ex) {
					logger.info("Some changes has done in token !! invalid Token");
					ex.printStackTrace();
				}catch (Exception ex) {
				    ex.printStackTrace();
				}
		}else {
			logger.info("Invalid Header Value !!");
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			//Fetch User Detail
			UserDetails userDetails= this.detailsServiceImpl.loadUserByUsername(userName);
			Boolean validateToken  = this.helper.validateToken(token, userDetails);
			if(validateToken) {
				
				//Set the authentication
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}else {
				logger.info("Validation fails !!");
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
