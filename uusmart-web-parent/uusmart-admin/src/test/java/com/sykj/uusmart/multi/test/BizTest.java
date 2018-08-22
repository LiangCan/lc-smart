package com.sykj.uusmart.multi.test;

import com.sykj.uusmart.base.BaseJunit;
import com.sykj.uusmart.multi.service.TestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务测试
 *
 * @author fengshuonan
 * @date 2017-06-23 23:12
 */
public class BizTest extends BaseJunit {

    @Autowired
    TestService testService;

    @Test
    public void test() {
        testService.testGuns();

        testService.testBiz();

        //testService.testAll();
    }
}
