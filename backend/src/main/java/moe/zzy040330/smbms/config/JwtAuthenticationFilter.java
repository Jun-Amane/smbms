/**
* Package: moe.zzy040330.smbms.config
* File: JwtAuthenticationFilter.java
* Author: Ziyu ZHOU
* Date: 04/01/2025
* Time: 15:08
* Description: JWT authentication filter that intercepts HTTP requests to validate JWT tokens
* and establish security context for authenticated users.
*/
package moe.zzy040330.smbms.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import moe.zzy040330.smbms.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
* A filter that processes JWT authentication for incoming HTTP requests.
* This filter extends OncePerRequestFilter to ensure it is executed only once per request.
* It validates JWT tokens from the Authorization header and establishes security context
* for authenticated users.
*/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
   private final HandlerExceptionResolver handlerExceptionResolver;
   private final JwtService jwtService;
   private final UserDetailsService userDetailsService;

   public JwtAuthenticationFilter(
           JwtService jwtService,
           UserDetailsService userDetailsService,
           HandlerExceptionResolver handlerExceptionResolver
   ) {
       this.jwtService = jwtService;
       this.userDetailsService = userDetailsService;
       this.handlerExceptionResolver = handlerExceptionResolver;
   }

   /**
    * Sends an error response to the client with a 401 Unauthorized status.
    *
    * @param response The HTTP response to write the error message to
    * @param message The error message to be included in the response
    * @throws IOException If an I/O error occurs while writing the response
    */
   private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
       response.setContentType("application/json");
       response.setCharacterEncoding("UTF-8");

       String jsonResponse = String.format("{\"error\": \"Authentication failed\", \"message\": \"%s\"}", message);
       response.getWriter().write(jsonResponse);
   }

   /**
    * Determines whether the filter should not be applied to this request.
    * Skips filtering for authentication endpoints (/auth/*).
    *
    * @param request The HTTP request to check
    * @return true if the request should not be filtered, false otherwise
    */
   @Override
   protected boolean shouldNotFilter(HttpServletRequest request) {
       String path = request.getServletPath();
       return path.startsWith("/api/auth/");
   }

   /**
    * Processes the incoming HTTP request to authenticate using JWT.
    * Validates the JWT token from the Authorization header and sets up the security context
    * if the token is valid.
    *
    * @param request The HTTP request to process
    * @param response The HTTP response
    * @param filterChain The filter chain to continue processing
    * @throws ServletException If an error occurs during request processing
    * @throws IOException If an I/O error occurs during request processing
    */
   @Override
   protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
   ) throws ServletException, IOException {
       final String authHeader = request.getHeader("Authorization");

       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           sendErrorResponse(response, "Missing or invalid Authorization header");
           return;
       }

       try {
           final String jwt = authHeader.substring(7);
           final String userCode = jwtService.extractUsername(jwt);

           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

           if (userCode != null && authentication == null) {
               UserDetails userDetails = this.userDetailsService.loadUserByUsername(userCode);

               if (jwtService.isTokenValid(jwt, userDetails)) {
                   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                           userDetails,
                           null,
                           userDetails.getAuthorities()
                   );

                   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authToken);
               }
           }

           filterChain.doFilter(request, response);
       } catch (Exception exception) {
           handlerExceptionResolver.resolveException(request, response, null, exception);
       }
   }
}
