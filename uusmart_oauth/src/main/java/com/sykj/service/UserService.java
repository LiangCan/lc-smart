package com.sykj.service;

import com.sykj.mapper.MemberMapper;
import com.sykj.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true, propagation = Propagation.REQUIRED,
    rollbackFor = Exception.class)
public class UserService implements UserDetailsService {
  @Autowired
  private MemberMapper memberMapper;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    Member member = memberMapper.queryByMobile(username);
    if (null == member) {
      throw new UsernameNotFoundException("Bad credentials");
    }
    member.setUsername(username);
    return member;
  }

}
