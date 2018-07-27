package com.sykj.mapper;

import com.sykj.model.Member;
import tk.mybatis.mapper.common.Mapper;

public interface MemberMapper extends Mapper<Member> {

  Member queryByMobile(String mobile);
}
