package com.sykj.config.wrapper;

import com.sykj.model.Member;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemberTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		Member member = (Member) authentication.getPrincipal();
		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("id", member.getUserId());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		((DefaultOAuth2AccessToken) accessToken).setExpiration(getDateAfter(new Date(), 3));
		return accessToken;
	}

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
}
