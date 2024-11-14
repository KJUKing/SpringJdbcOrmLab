package kr.or.ddit.lab01.conf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceJavaConfig {


    @Bean
    public PropertiesFactoryBean dbInfo(@Value("classpath:kr/or/ddit/db/DBInfo.properties") Resource location){
        PropertiesFactoryBean pfb = new PropertiesFactoryBean();
        pfb.setLocation(location);
        return pfb;
    }

    @Bean
    public DataSource dataSource(
            Properties dbInfo,
            @Value("#{dbInfo.driverClassName}") String driverClassName
    ) {
        DriverManagerDataSource inst = new DriverManagerDataSource();
        inst.setDriverClassName(driverClassName);
        inst.setUrl(dbInfo.getProperty("url"));
        inst.setUsername(dbInfo.getProperty("user"));
        inst.setPassword(dbInfo.getProperty("password"));
        return inst;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate inst = new JdbcTemplate();
        inst.setDataSource(dataSource);
        return inst;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        NamedParameterJdbcTemplate inst = new NamedParameterJdbcTemplate(dataSource);
        return inst;
    }
}
