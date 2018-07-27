package com.sykj.config.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointWrapper extends OAuth2AuthenticationEntryPoint {

  private static Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointWrapper.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {

    logger.info("未授权认证前,拒绝访问.");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
  }

}
