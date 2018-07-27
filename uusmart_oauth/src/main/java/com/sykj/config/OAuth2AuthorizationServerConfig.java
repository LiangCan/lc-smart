package com.sykj.config;


import com.sykj.config.wrapper.DefaultOAuth2RequestFactoryWrapper;
import com.sykj.config.wrapper.JsonSerializationStrategy;
import com.sykj.config.wrapper.MemberTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(ResourceServerProperties.class)
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private RedisConnectionFactory factory;
	@Autowired
	@Qualifier("developerService")
	private ClientDetailsService clientDetailsService; // 开发者帐号
	@Autowired
	private UserDetailsService userDetailsService; // 使用者用户帐号

	@Autowired
	private ResourceServerProperties resourceServerProperties;

	@Bean
	public RedisTokenStoreSerializationStrategy redisTokenStoreSerializationStrategy() {
		return new JsonSerializationStrategy();
	}

	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore redisTokenStore = new RedisTokenStore(factory);
//		redisTokenStore.setSerializationStrategy(redisTokenStoreSerializationStrategy());
		return redisTokenStore;
	}

	@Bean
	public ApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new MemberTokenEnhancer();
	}

	@Bean
	public OAuth2RequestFactory oAuth2RequestFactory() {
		return new DefaultOAuth2RequestFactoryWrapper(this.clientDetailsService);
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		clients.withClientDetails(clientDetailsService).withClient(this.resourceServerProperties.getServiceId());
	}
//	{
//		"access_token": "e8bfee17-cc95-4fd4-b672-cdd5f0603e25",
//			"token_type": "bearer",
//			"refresh_token": "7969287f-b6e1-41de-9e3f-044d88c500ce",
//			"expires_in": 259199,
//			"scope": "user",
//			"id": 2
//	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		// TODO Auto-generated method stub
		endpoints
				.tokenGranter( new DiyAuthorizationCodeTokenGranter(endpoints.getTokenServices()  ,endpoints.getAuthorizationCodeServices() ,clientDetailsService ,this.oAuth2RequestFactory()))
				.requestFactory(this.oAuth2RequestFactory()).tokenEnhancer(tokenEnhancer())
				 .pathMapping("/oauth/confirm_access", "/oauth/confirmAccess")
//				.pathMapping("/hello", "/hello")
				.authenticationManager(authenticationManager).tokenStore(this.tokenStore())
				.userDetailsService(userDetailsService).approvalStore(this.approvalStore());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}
}
