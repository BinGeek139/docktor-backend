package com.techasians.doctor.subscriber.security.jwt;

import com.google.gson.Gson;
import com.techasians.doctor.common.respond.ResponseData;
import com.techasians.doctor.common.utils.CommonUtils;
import com.techasians.doctor.subscriber.utils.Constants;
import com.techasians.doctor.subscriber.web.rest.errors.DuplicateTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        httpServletRequest.getContextPath();
        System.out.println(httpServletRequest.getRequestURI());
        try {
            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DuplicateTokenException duplicateTokenException) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setContentType("application/json");
            PrintWriter printWriter = httpServletResponse.getWriter();
            Gson gson = new Gson();
            ResponseData responseData = new ResponseData();
            responseData.setErrorCode("4");
            responseData.setMessage(translateByLanguage());
            responseData.setData("");
            String responseDataStr = gson.toJson(responseData);
            printWriter.println(responseDataStr);
        }
    }

    public String translateByLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        if("vi".equals(locale.getLanguage())){
            return "Tài Khoản đã được đăng nhập bằng một thiết bị khác";
        }
        return "Account already logged in elsewhere";
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
