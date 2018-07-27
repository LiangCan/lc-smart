package com.sykj.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Table(name = "oc_developer")
public class Developer implements ClientDetails {

  private static final long serialVersionUID = 7318058928169089457L;
  @Id
  private long id;

  private String clientId;
  private String clientSecret;

  @Column(name = "grant_types")
  private String grantTypes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public void setGrantTypes(String grantTypes) {
    this.grantTypes = grantTypes;
  }

  public Developer() {
    this.clientId = "";
    this.clientSecret = "";
    this.grantTypes = "";
  }

  public Developer(String clientId, String clientSecret, String grantTypes) {
    this();
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.grantTypes = grantTypes;
  }

  @Override
  public String getClientId() {
    // TODO Auto-generated method stub
    return this.clientId;
  }

  @Override
  public Set<String> getResourceIds() {
    // TODO Auto-generated method stub
    return Sets.newHashSet();
  }

  @Override
  public boolean isSecretRequired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public String getClientSecret() {
    // TODO Auto-generated method stub
    return this.clientSecret;
  }

  @Override
  public boolean isScoped() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Set<String> getScope() {
    // TODO Auto-generated method stub
    Set<String> scope =  new HashSet<>();
    scope.add("user");
    return scope;
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    // TODO Auto-generated method stub
    Set<String> grantTypeSet = Sets.newHashSet();
    if (StringUtils.hasText(this.grantTypes)) {
      StringTokenizer tokenizer = new StringTokenizer(this.grantTypes, ",");
      while (tokenizer.hasMoreElements()) {
        grantTypeSet.add(tokenizer.nextToken().trim());
      }
    }
    System.out.println(" grantTypes = " + grantTypeSet);
    return grantTypeSet;
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    // TODO Auto-generated method stub
//    Set<String> redirectSet = Sets.newHashSet();
//    redirectSet.add("https://open.bot.tmall.com/oauth/callback");
    return null;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    GrantedAuthority authority = new SimpleGrantedAuthority("USER");
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(authority);
    return authorities;
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isAutoApprove(String scope) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    // TODO Auto-generated method stub
    return null;
  }

}
