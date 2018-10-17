package net.abcbook.framework.base.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid 连接时的数据库配置内容
 *
 * @author summer
 * @date 2018/10/17 10:46 AM
 * @version V1.0.0-RELEASE
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;

    /**
     * 配置 DataSource bean
     *
     * @author summer
     * @date 2018/10/17 10:47 AM
     * @param  @Bean 声明其为Bean实例
     * @param @Primary 在同样的DataSource中，首先使用被标注的DataSource
     * @return javax.sql.DataSource
     * @version V1.0.0-RELEASE
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        // 创建连接对象
        DruidDataSource datasource = new DruidDataSource();

        // 连接地址
        datasource.setUrl(url);
        // 用户名
        datasource.setUsername(username);
        // 密码
        datasource.setPassword(password);
        // 驱动器
        datasource.setDriverClassName(driverClassName);

        //configuration
        // 初始化数量
        datasource.setInitialSize(initialSize);
        // 最小值
        datasource.setMinIdle(minIdle);
        // 最大值
        datasource.setMaxActive(maxActive);
        // 连接等待超时的时间
        datasource.setMaxWait(maxWait);
        // 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }
        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }
}
