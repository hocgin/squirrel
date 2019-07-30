package in.hocg.squirrel.devtools;

import in.hocg.squirrel.devtools.reload.MybatisMapperReload;
import in.hocg.squirrel.devtools.sql.MybatisWatchSql;
import in.hocg.squirrel.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Configuration
@EnableConfigurationProperties(DevtoolsProperties.class)
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class DevtoolsAutoConfiguration implements InitializingBean,
        ApplicationContextAware {
    private ApplicationContext applicationContext;
    private SqlSessionFactory sqlSessionFactory;
    
    @Bean
    @ConditionalOnMissingBean
    public MybatisMapperReload mybatisAutoConfiguration() {
        return new MybatisMapperReload(applicationContext);
    }
    @Bean
    @ConditionalOnMissingBean
    public MybatisWatchSql mybatisWatchSql() {
        return new MybatisWatchSql(applicationContext);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        this.sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
