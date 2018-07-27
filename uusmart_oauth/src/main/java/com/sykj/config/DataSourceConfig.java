package com.sykj.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.sykj.config.DataSourceConfig.DruidDataSourceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@EnableTransactionManagement
@MapperScan("com/sykj/mapper")
@EnableConfigurationProperties({DataSourceProperties.class, DruidDataSourceProperties.class})
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class DataSourceConfig implements TransactionManagementConfigurer {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private DataSourceProperties dataSourceProperties;
  @Autowired
  private DruidDataSourceProperties druidDataSourceProperties;

  @Bean
  public ServletRegistrationBean druidServlet() {
    return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new WebStatFilter());
    filterRegistrationBean.addUrlPatterns("/");
    filterRegistrationBean.addInitParameter("exclusions", ".js,.gif,.jpg,.png,.css,.ico,/druid/");
    return filterRegistrationBean;
  }

  @Bean
  public DataSource dataSource() {
    DruidDataSource ds = new DruidDataSource();

    ds.setDbType(JdbcConstants.MYSQL);

    ds.setDriverClassName(dataSourceProperties.getDriverClassName());
    ds.setUrl(dataSourceProperties.getUrl());
    ds.setUsername(dataSourceProperties.getUsername());
    ds.setPassword(dataSourceProperties.getPassword());

    ds.setInitialSize(druidDataSourceProperties.getInitialSize());
    ds.setMinIdle(druidDataSourceProperties.getMinIdle());
    ds.setMaxActive(druidDataSourceProperties.getMaxActive());
    ds.setMaxWait(druidDataSourceProperties.getMaxWait());
    ds.setTimeBetweenConnectErrorMillis(
        druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
    ds.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
    ds.setValidationQuery(druidDataSourceProperties.getValidationQuery());
    // ds.setValidationQueryTimeout(druidDataSourceProperties.getValidationQueryTimeout());
    ds.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
    ds.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
    ds.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
    ds.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());

    // ds.setQueryTimeout(druidDataSourceProperties.getQueryTimeout());
    // ds.setTransactionQueryTimeout(druidDataSourceProperties.getTransactionQueryTimeout());
    // ds.setLoginTimeout(druidDataSourceProperties.getLoginTimeout());

    // ds.setValidConnectionChecker();
    // ds.setExceptionSorter();
    ds.setDefaultAutoCommit(druidDataSourceProperties.isDefaultAutoCommit());
    // ds.setDefaultReadOnly(druidDataSourceProperties.isDefaultReadOnly());
    // ds.setDefaultTransactionIsolation();

    // ds.setRemoveAbandoned(druidDataSourceProperties.isRemoveAbandoned());
    // ds.setRemoveAbandonedTimeout(druidDataSourceProperties.getRemoveAbandonedTimeout());
    // ds.setLogAbandoned(druidDataSourceProperties.isLogAbandoned());

    try {
      ds.setFilters(druidDataSourceProperties.getFilters());
    } catch (SQLException e) {
      logger.error("error when set filters: " + druidDataSourceProperties.getFilters(), e);
    }

    // Properties prop = new Properties();
    // prop.setProperty("druid.stat.mergeSql", Boolean.toString(conProp.isMergeSql()));
    // prop.setProperty("druid.stat.slowSqlMillis", Long.toString(conProp.getSlowSqlMillis()));
    // prop.setProperty("config.decrypt", Boolean.toString(conProp.isDecrypt()));
    // prop.setProperty("config.decrypt.key", conProp.getDecryptKey());
    // prop.setProperty("druid.oracle.pingTimeout", Integer.toString(conProp.getPingTimeout()));
    //
    // ds.setConnectProperties(prop);
    //
    // ds.setUseGlobalDataSourceStat(druidDataSourceProperties.isUseGlobalDataSourceStat());
    return ds;
  };

  @Bean
  @Override
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    // TODO Auto-generated method stub
    return new DataSourceTransactionManager(this.dataSource());
  }

  @ConfigurationProperties(prefix = "spring.datasource")
  public static class DruidDataSourceProperties {
    private String filters;
    private int maxActive;
    private int initialSize;
    private int maxWait;
    private int minIdle;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;

    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private boolean defaultAutoCommit;

    public String getFilters() {
      return filters;
    }

    public void setFilters(String filters) {
      this.filters = filters;
    }

    public int getMaxActive() {
      return maxActive;
    }

    public void setMaxActive(int maxActive) {
      this.maxActive = maxActive;
    }

    public int getInitialSize() {
      return initialSize;
    }

    public void setInitialSize(int initialSize) {
      this.initialSize = initialSize;
    }

    public int getMaxWait() {
      return maxWait;
    }

    public void setMaxWait(int maxWait) {
      this.maxWait = maxWait;
    }

    public int getMinIdle() {
      return minIdle;
    }

    public void setMinIdle(int minIdle) {
      this.minIdle = minIdle;
    }

    public int getTimeBetweenEvictionRunsMillis() {
      return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
      this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
      return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
      this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
      return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
      this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
      return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
      this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
      return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
      this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
      return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
      this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
      return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
      this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxOpenPreparedStatements() {
      return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
      this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public boolean isDefaultAutoCommit() {
      return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
      this.defaultAutoCommit = defaultAutoCommit;
    }
  }
}
