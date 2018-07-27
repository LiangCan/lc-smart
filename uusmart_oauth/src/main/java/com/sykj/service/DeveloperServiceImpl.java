package com.sykj.service;

import com.sykj.mapper.DeveloperMapper;
import com.sykj.mapper.DeveloperMapper;
import com.sykj.model.Developer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("developerService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true, propagation = Propagation.REQUIRED,
    rollbackFor = Exception.class)
public class DeveloperServiceImpl implements ClientDetailsService {

  private static Logger logger = LoggerFactory.getLogger(DeveloperServiceImpl.class);

  @Autowired
  private DeveloperMapper developerMapper;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    // TODO Auto-generated method stub
    logger.debug("clientId: {}", clientId);

    Developer developer = this.developerMapper.queryByClientId(clientId);
    if(developer == null){
      logger.debug("clientId: "+clientId +"  is null ============" );
      throw new ClientRegistrationException(clientId);
    }
//    for(String str : developer.getAuthorizedGrantTypes()){
//      logger.info("GrantTypes :   ========="+str );
//    }
    return  developer;

    // return new Developer(clientId, "123", "password, refresh_token");
    // return new Developer(clientId, "123", Sets.newHashSet("authorization_code, refresh_token"));
    // return new Developer(clientId, "123", "authorization_code, refresh_token");
  }

}
