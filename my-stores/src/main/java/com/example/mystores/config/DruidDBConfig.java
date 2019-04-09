package com.example.mystores.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * <p>
 * <strong>
 *     Druid 的 Configruation
 * </strong><br /><br />
 *
 * https://blog.csdn.net/shuxing520/article/details/78324613
 * </p>
 *
 * @author chengchao - 2018/6/14 21:25 <br />
 * @since 1.0
 */
@Configuration
public class DruidDBConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DruidDBConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Bean     // 声明其为Bean实例
    @Primary  // 在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(this.dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);


        LOGGER.debug("dbUrl: {}", dbUrl);
        LOGGER.debug("username: {}", username);
        LOGGER.debug("password: {}", password);
        LOGGER.debug("driverClassName: {}", driverClassName);

        // configuration
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);

        LOGGER.debug("initialSize: {}", initialSize);
        LOGGER.debug("minIdle: {}", minIdle);
        LOGGER.debug("maxActive: {}", maxActive);
        LOGGER.debug("maxWait: {}", maxWait);

        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);


        LOGGER.debug("timeBetweenEvictionRunsMillis: {}", timeBetweenEvictionRunsMillis);
        LOGGER.debug("minEvictableIdleTimeMillis: {}", minEvictableIdleTimeMillis);
        LOGGER.debug("validationQuery: {}", validationQuery);
        LOGGER.debug("testWhileIdle: {}", testWhileIdle);

        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);


        LOGGER.debug("testOnBorrow: {}", testOnBorrow);
        LOGGER.debug("testOnReturn: {}", testOnReturn);
        LOGGER.debug("poolPreparedStatements: {}", poolPreparedStatements);
        LOGGER.debug("maxPoolPreparedStatementPerConnectionSize: {}", maxPoolPreparedStatementPerConnectionSize);



        dataSource.setConnectionProperties(connectionProperties);
        LOGGER.debug("connectionProperties: {}", connectionProperties);
        LOGGER.debug("filters: {}", filters);

        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            LOGGER.error("druid configuration initialization filter : {}", e);
        }
        dataSource.setConnectionProperties(connectionProperties);

        return dataSource;
    }

}
