package alessandro.angheben.u5w2d5.security;

import alessandro.angheben.u5w2d5.entities.Employee;
import alessandro.angheben.u5w2d5.exceptions.UnauthorizedException;
import alessandro.angheben.u5w2d5.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autHeader = request.getHeader("Authorization");
        if (autHeader == null || !autHeader.startsWith("Bearer "))
            throw new UnauthorizedException("please insert token");

        String accessToken = autHeader.substring(7);
        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        Employee employee = employeeService.findById(UUID.fromString(id));
        Authentication authentication = new UsernamePasswordAuthenticationToken(employee,null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){

        return new AntPathMatcher().match("/auth/**", request.getServletPath());

    }
}
