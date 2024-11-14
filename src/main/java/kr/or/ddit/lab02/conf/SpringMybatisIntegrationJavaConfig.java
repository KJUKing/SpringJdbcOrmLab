package kr.or.ddit.lab02.conf;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "kr.or.ddit.mbti")
public class SpringMybatisIntegrationJavaConfig {


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
    public TransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager inst = new DataSourceTransactionManager();
        inst.setDataSource(dataSource);
        return inst;
    }

//
//    FactoryBean : FactoryBean이 bean으로 등록된 경우 실제 등록되는 빈의 인스턴스는
//                    factory내부 getObject메소드의 반환객체에서 생성되는 객체
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(
            @Value("classpath:kr/or/ddit/mybatis/Configuration.xml") Resource configLocation
            , DataSource dataSource
            , @Value("classpath:kr/or/ddit/mybatis/mappers/*.xml") Resource...mapperLocations
    ) {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setConfigLocation(configLocation);
        factory.setDataSource(dataSource);
        factory.setMapperLocations(mapperLocations);
        return factory;
    }

    // template : 반복되는 패턴으로 중복되는 작업을 미리 코드화 시켜놓은 api
    // JdbcTemplate -> NamedParameterJdbcTemplate(? 대신 :name으로 쿼리 파라미터 식별)
    // SqlSessionTemplate : SqlSession 사용시 반복되는 작업을 미리 코드화시켜놓음
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        SqlSessionTemplate inst = new SqlSessionTemplate(sqlSessionFactory);
        return inst;
    }

//    mapper scanner : 특정 패키지내의 일부 인터페이스를 대상으로 매퍼 프록시를 생성하고 어플리케이션내에서 싱글톤으로 관리하기 위한 설정
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer inst = new MapperScannerConfigurer();
        inst.setSqlSessionFactoryBeanName("sqlSessionFactory");
        inst.setBasePackage("kr.or.ddit.**.dao");
        inst.setAnnotationClass(Mapper.class);
        return inst;
    }


}
