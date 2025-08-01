package mymovielist.mymovielist.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Used to filter http requests
 * @author Andrew Gee
 */
@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil; //provides methods for creating token, extracting username from token, and checking token and email

    /**
     * Filters http requests
     * @param request incoming http request
     * @param response the response after the filter executes
     * @param filterChain chain of filters that process each http request
     * @throws ServletException when something is wrong in servlet request
     * @throws IOException when error with input and output
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header != null){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                String email = jwtUtil.extractUsername(token);
                if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
                   if(jwtUtil.validateToken(email,token)){
                       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, null,null);
                       SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                       filterChain.doFilter(request, response); //continue with other filters
                       return;
                   }
                }
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Unauthorized");
    }
}
