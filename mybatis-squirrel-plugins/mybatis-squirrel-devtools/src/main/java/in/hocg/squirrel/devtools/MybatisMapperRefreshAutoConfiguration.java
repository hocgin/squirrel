package in.hocg.squirrel.devtools;

import in.hocg.squirrel.spring.boot.autoconfigure.MybatisAutoConfiguration;
import in.hocg.squirrel.spring.boot.autoconfigure.MybatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@org.springframework.context.annotation.Configuration
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class MybatisMapperRefreshAutoConfiguration implements InitializingBean, ApplicationContextAware {
    private ApplicationContext applicationContext;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("启动监听🐕");
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        MybatisProperties mybatisProperties = applicationContext.getBean(MybatisProperties.class);
        Resource[] mapperResources = mybatisProperties.resolveMapperLocations();
        new Thread(()-> new WatchDog(mapperResources, sqlSessionFactory, true)).start();
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
