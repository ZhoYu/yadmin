/**
 * <p>
 * 文件名称:    JwtAuthorizationTokenFilter
 * </p>
 */
package com.zhou.yadmin.system.core.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.zhou.yadmin.common.constants.FrontConstant;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:04
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    private final String tokenHeader;

    public JwtAuthorizationTokenFilter(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, JwtTokenHelper jwtTokenHelper,
      @Value("${jwt.header}") String tokenHeader)
    {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.tokenHeader = tokenHeader;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException
    {
        logger.debug("processing authentication for '{}'", request.getRequestURL());
        final String requestHeader = request.getHeader(this.tokenHeader);

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith(FrontConstant.HEADER_AUTHORIZATION_TOKEN_PREFIX))
        {
            authToken = requestHeader.substring(7);
            try
            {
                username = jwtTokenHelper.getUsernameFromToken(authToken);
            }
            catch (ExpiredJwtException e)
            {
                logger.error(e.getMessage());
            }
        }
        else
        {
            logger.debug("couldn't find bearer string, will ignore the header");
        }

        logger.debug("checking authentication for user '{}'", username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            logger.debug("security context was null, so authorizating user");
            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtTokenHelper.validateToken(authToken, userDetails))
            {
                UsernamePasswordAuthenticationToken authentication =
                  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authorizated user '{}', setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
