package in.hocg.squirrel.provider.select;

import in.hocg.squirrel.provider.BaseProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class SelectOneProvider extends BaseProvider {
    
    public SelectOneProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    public void selectOne(MappedStatement mappedStatement) {
        log.debug("初始化 {}", mappedStatement.getId());
    }
}
