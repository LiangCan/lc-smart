package com.sykj.config.wrapper;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonSerializationStrategy extends StandardStringSerializationStrategy {

	@SuppressWarnings("rawtypes")
	private static final Map<Class, RedisSerializer> serializerMap = new ConcurrentHashMap<>();
	static {
		serializerMap.put(OAuth2AccessToken.class, new Jackson2JsonRedisSerializer<>(OAuth2AccessToken.class));
		serializerMap.put(DefaultOAuth2AccessToken.class, new Jackson2JsonRedisSerializer<>(DefaultOAuth2AccessToken.class));
		serializerMap.put(OAuth2Authentication.class, new JdkSerializationRedisSerializer());
		serializerMap.put(OAuth2RefreshToken.class, new Jackson2JsonRedisSerializer<>(OAuth2RefreshToken.class));
		serializerMap.put(DefaultExpiringOAuth2RefreshToken.class, new Jackson2JsonRedisSerializer<>(DefaultExpiringOAuth2RefreshToken.class));
		serializerMap.put(Object.class, new Jackson2JsonRedisSerializer<>(Object.class));
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
		// TODO Auto-generated method stub
		return (T) serializerMap.get(clazz).deserialize(bytes);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected byte[] serializeInternal(Object object) {
		// TODO Auto-generated method stub
		return serializerMap.get(object.getClass()).serialize(object);
	}

}
