package in.hocg.squirrel.cashew;

import in.hocg.squirrel.spring.boot.autoconfigure.MybatisAutoConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CashewProperties.class)
@AutoConfigureAfter({MybatisAutoConfiguration.class})
@RequiredArgsConstructor
public class CashewAutoConfiguration implements InitializingBean,
        ApplicationContextAware {
    private ApplicationContext applicationContext;
    private final CashewProperties cashewProperties;
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        sqlSessionFactory.getConfiguration().addInterceptor(new CashewInterceptor());
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
