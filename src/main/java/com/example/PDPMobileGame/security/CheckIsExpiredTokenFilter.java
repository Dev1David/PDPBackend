package com.example.PDPMobileGame.security;

import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CheckIsExpiredTokenFilter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String mapToJsonString(Map<String, Object> data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (
                !pathMatcher.match("/api/v1/users/login", request.getRequestURI())
                && !pathMatcher.match("/api/v1/users/create", request.getRequestURI())
        ) {
            String token = null;

            if (request.getHeader("Authorization") != null) {
                token = request.getHeader("Authorization").substring(7);
                if (jwtTokenProvider.isTokenExpired(token).isValid()) {
                    UserEntity user = userRepository.findByToken(token);
                    if (user == null) {
                        Map<String, Object> resp = new HashMap<>();
                        resp.put("error", "Permission denied");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getWriter().write(mapToJsonString(resp));
                    } else {
                        filterChain.doFilter(request, response);
                    }
                } else {
                    HashMap<String, Object> resp = new HashMap<>();
                    resp.put("error", jwtTokenProvider.isTokenExpired(token).getErrorResponse().getBody());
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(mapToJsonString(resp));
                }
            } else {
                Map<String, Object> resp = new HashMap<>();
                resp.put("error", "Permission denied");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(mapToJsonString(resp));
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
