package com.sykj.config.wrapper;

import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class DefaultOAuth2RequestFactoryWrapper extends DefaultOAuth2RequestFactory {

  public DefaultOAuth2RequestFactoryWrapper(ClientDetailsService clientDetailsService) {
    super(clientDetailsService);
    // super.setCheckUserScopes(true);
  }

  @Override
  public AuthorizationRequest createAuthorizationRequest(
      Map<String, String> authorizationParameters) {


    String clientId = authorizationParameters.get(OAuth2Utils.CLIENT_ID);
    if (!StringUtils.hasText(clientId)) {
      throw new InvalidClientException("A client id must be provided.");
    }

    String redirectUri = authorizationParameters.get(OAuth2Utils.REDIRECT_URI);
    if (!StringUtils.hasText(redirectUri)) {
      throw new InvalidClientException("A redirect_uri must be supplied.");
    }

    String responseType = authorizationParameters.get(OAuth2Utils.RESPONSE_TYPE);
    if (!StringUtils.hasText(responseType)) {
      throw new InvalidClientException("A response_type must be supplied.");
    }
    authorizationParameters.put(OAuth2Utils.SCOPE,"user");
    String scope = authorizationParameters.get(OAuth2Utils.SCOPE);
    if (!StringUtils.hasText(scope)) {
      throw new InvalidClientException("A scope must be supplied.");
    }

    return super.createAuthorizationRequest(authorizationParameters);
  }


}
