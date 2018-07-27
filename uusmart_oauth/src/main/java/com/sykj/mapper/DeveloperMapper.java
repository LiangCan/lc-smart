package com.sykj.mapper;

import com.sykj.model.Developer;
import tk.mybatis.mapper.common.Mapper;

public interface DeveloperMapper extends Mapper<Developer> {

  Developer queryByClientId(String clientId);
}
