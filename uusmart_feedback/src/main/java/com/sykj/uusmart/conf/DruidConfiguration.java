package com.sykj.uusmart.conf;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * druid 配置.
 * <p>
 * 这样的方式不需要添加注解：@ServletComponentScan
 *
 * @author Administrator
 */
@Configuration
public class DruidConfiguration {

//    /**
//     * 注册一个StatViewServlet
//     *
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean DruidStatViewServle2() {
//        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//
//        //添加初始化参数：initParams
//
//        //白名单：
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
//        //登录查看信息的账号密码.
//        servletRegistrationBean.addInitParameter("loginUsername", "admin2");
//        servletRegistrationBean.addInitParameter("loginPassword", "123456");
//        //是否能够重置数据.
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }

    /**
     * druid监控视图配置
     *
     * @author linge
     * @ClassName: DruidStatViewServlet
     * @date 2017年7月24日 上午10:54:27
     */
    @WebServlet(urlPatterns = "/druid/*", initParams = {@WebInitParam(name = "allow", value = ""),// IP白名单 (没有配置或者为空，则允许所有访问)
            @WebInitParam(name = "deny", value = "192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
            @WebInitParam(name = "loginUsername", value = "admin"),// 用户名
            @WebInitParam(name = "loginPassword", value = "admin"),// 密码
            @WebInitParam(name = "resetEnable", value = "true")// 禁用HTML页面上的“Reset All”功能
    })
    public class DruidStatViewServlet extends StatViewServlet {
        private static final long serialVersionUID = 2359758657306626394L;

    }

    /**
     * 配置监控拦截器
     * druid监控拦截器
     * @ClassName: DruidStatFilter
     * @author linge
     * @date 2017年7月24日 上午10:53:40
     */
    @WebFilter(filterName="druidWebStatFilter",
            urlPatterns="/*",
            initParams={
                    @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),// 忽略资源
            })
    public class DruidStatFilter extends WebStatFilter {

    }

}