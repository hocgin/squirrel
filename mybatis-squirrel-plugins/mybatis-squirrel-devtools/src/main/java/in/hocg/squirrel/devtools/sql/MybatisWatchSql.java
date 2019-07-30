package in.hocg.squirrel.devtools.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class MybatisWatchSql {
    
    public MybatisWatchSql(ApplicationContext applicationContext) {
        log.debug("启动 监听SQL");
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        sqlSessionFactory.getConfiguration().addInterceptor(new WatchSqlInterceptor());
    }
}
