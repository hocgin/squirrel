package in.hocg.squirrel.devtools;

import in.hocg.squirrel.devtools.reload.WatchDog;
import in.hocg.squirrel.devtools.sql.WatchSqlInterceptor;
import in.hocg.squirrel.spring.boot.autoconfigure.MybatisAutoConfiguration;
import in.hocg.squirrel.spring.boot.autoconfigure.MybatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DevtoolsProperties.class)
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class DevtoolsAutoConfiguration implements InitializingBean,
        ApplicationContextAware {
    private ApplicationContext applicationContext;
 
    @Override
    public void afterPropertiesSet() throws Exception {
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        MybatisProperties mybatisProperties = applicationContext.getBean(MybatisProperties.class);
        
        log.debug("==========[[启动监听 SQL]]==========");
        sqlSessionFactory.getConfiguration().addInterceptor(new WatchSqlInterceptor());
    
        log.debug("==========[[启动 Mapper 监听🐕]]==========");
        Resource[] mapperResources = mybatisProperties.resolveMapperLocations();
        new Thread(() -> new WatchDog(mapperResources, sqlSessionFactory, true), "MybatisMapperReload").start();
        
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
