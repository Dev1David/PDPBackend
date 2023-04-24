package com.example.PDPMobileGame.security;

import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomRequestFilter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (
            pathMatcher.match("/api/v1/admin", request.getRequestURI())
        ) {
            String token = request.getHeader("Authorization").substring(7);

            UserEntity user = userRepository.findByToken(token);

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map <String, Object> resp = new HashMap<>();
                resp.put("error", "Access denied");
                resp.put("status", HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(mapToJsonString(resp));
                return;
            }

            if (user.getRole().getRoleName().equals("ADMIN")) {
                filterChain.doFilter(request, response);
            } else {
                Map <String, Object> json = new HashMap<>();
                json.put("error", "Access denied");
                json.put("status", HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(mapToJsonString(json));
            }
        } else {
            filterChain.doFilter(request,response);
        }
    }

    public String mapToJsonString(Map<String, Object> data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}
