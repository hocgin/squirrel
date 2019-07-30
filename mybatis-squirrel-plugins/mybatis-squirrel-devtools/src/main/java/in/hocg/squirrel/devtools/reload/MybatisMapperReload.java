package in.hocg.squirrel.devtools.reload;

import in.hocg.squirrel.spring.boot.autoconfigure.MybatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class MybatisMapperReload {
    
    public MybatisMapperReload(ApplicationContext applicationContext) {
        log.debug("启动 Mapper 监听🐕");
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        MybatisProperties mybatisProperties = applicationContext.getBean(MybatisProperties.class);
        Resource[] mapperResources = mybatisProperties.resolveMapperLocations();
        new Thread(() -> new WatchDog(mapperResources, sqlSessionFactory, true), "MybatisMapperReload").start();
    }
}
