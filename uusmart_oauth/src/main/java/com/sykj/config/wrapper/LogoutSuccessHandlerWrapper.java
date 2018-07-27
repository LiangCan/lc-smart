package com.sykj.config.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OAuth认证退出成功时，需要处理的逻辑
 * 
 * @author Administrator
 *
 */
public class LogoutSuccessHandlerWrapper implements LogoutSuccessHandler {

  private static Logger logger = LoggerFactory.getLogger(LogoutSuccessHandlerWrapper.class);

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException, ServletException {
    // TODO Auto-generated method stub
    logger.debug("LogoutSuccessHandlerWrapper: {}", authentication.getName());


    response.setStatus(HttpStatus.OK.value());
  }

}
