package com.sykj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.HashMap;
import java.util.Map;

public class DiyAuthorizationCodeTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "authorization_code";
    private final AuthorizationCodeServices authorizationCodeServices;

    private ClientDetailsService clientDetailsService;

    public DiyAuthorizationCodeTokenGranter(AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected DiyAuthorizationCodeTokenGranter(AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.clientDetailsService = clientDetailsService;
        this.authorizationCodeServices = authorizationCodeServices;
    }

    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest){
            if("refresh_token".equals(grantType)){
                String refreshToken = (String)tokenRequest.getRequestParameters().get("refresh_token");
                return this.getTokenServices().refreshAccessToken(refreshToken, tokenRequest);
            }
            String clientId = tokenRequest.getClientId();
            ClientDetails client = this.clientDetailsService.loadClientByClientId(clientId);
            this.validateGrantType(grantType, client);
            this.logger.debug("Getting access token for: " + clientId);
            return this.getAccessToken(client, tokenRequest);
    }

    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        String authorizationCode = (String)parameters.get("code");
        String redirectUri = (String)parameters.get("redirect_uri");
        if(authorizationCode == null) {
            throw new InvalidRequestException("An authorization code must be supplied.");
        } else {
            OAuth2Authentication storedAuth = this.authorizationCodeServices.consumeAuthorizationCode(authorizationCode);
            if(storedAuth == null) {
                throw new InvalidGrantException("Invalid authorization code: " + authorizationCode);
            } else {
                OAuth2Request pendingOAuth2Request = storedAuth.getOAuth2Request();
//                String redirectUriApprovalParameter = (String)pendingOAuth2Request.getRequestParameters().get("redirect_uri");
//                if((redirectUri != null || redirectUriApprovalParameter != null) && !pendingOAuth2Request.getRedirectUri().equals(redirectUri)) {
//                    throw new RedirectMismatchException("Redirect URI mismatch.");
//                } else {
                    String pendingClientId = pendingOAuth2Request.getClientId();
                    String clientId = tokenRequest.getClientId();
                    if(clientId != null && !clientId.equals(pendingClientId)) {
                        throw new InvalidClientException("Client ID mismatch");
                    } else {
                        Map<String, String> combinedParameters = new HashMap(pendingOAuth2Request.getRequestParameters());
                        combinedParameters.putAll(parameters);
                        OAuth2Request finalStoredOAuth2Request = pendingOAuth2Request.createOAuth2Request(combinedParameters);
                        Authentication userAuth = storedAuth.getUserAuthentication();
                        return new OAuth2Authentication(finalStoredOAuth2Request, userAuth);
                    }
//                }
            }
        }
    }
}