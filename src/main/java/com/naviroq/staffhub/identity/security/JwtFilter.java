package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Core filter logic — executed for every incoming request.
     *
     * <p><strong>Flow:</strong>
     * <ol>
     *   <li>Extract Authorization header</li>
     *   <li>Parse and validate JWT token</li>
     *   <li>Load user from database using UUID in token subject</li>
     *   <li>Validate token against loaded user (prevents using token for deleted users)</li>
     *   <li>Set authentication in SecurityContext so {@code @PreAuthorize} works downstream</li>
     * </ol>
     *
     * <p><strong>Important:</strong> If no token is present or token is invalid, this filter
     * does NOT block the request. It simply doesn't set authentication. The request proceeds
     * and will be rejected later by Spring Security if the endpoint requires auth.
     *
     * <p><strong>Why UUID for subject:</strong> The JWT subject contains the user's UUID (not email
     * or username) because UUID is immutable. If email changes, old tokens remain valid.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response
     * @param filterChain the chain of filters to pass the request to next
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extract JWT from Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            // 2. Extract User ID (UUID) from the JWT's subject
            userId = jwtService.extractUserId(jwt);

            // 3. If we have a userId and no authentication is set yet
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 4. Fetch the User from the database (using the UUID from the token)
                User user = userRepository.findById(UUID.fromString(userId))
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // 5. Validate the token against the fetched user (checks UUID match + expiry)
                if (jwtService.validateToken(jwt, user)) {

                    // 6. Build the CustomUserDetails wrapper
                    CustomUserDetails userDetails = new CustomUserDetails(user);

                    // 7. Create Spring Security Authentication object
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 8. Set the authentication in the SecurityContext (user is now logged in for this request)
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Authenticated user with ID: {}", userId);
                }
            }
        } catch (Exception e) {
            // Token is invalid, expired, or user not found. Do NOT set authentication.
            log.warn("JWT validation failed: {}", e.getMessage());
        }

        // 9. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}