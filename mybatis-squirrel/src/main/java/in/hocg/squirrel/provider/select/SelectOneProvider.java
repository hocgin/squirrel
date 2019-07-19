package in.hocg.squirrel.provider.select;

import in.hocg.squirrel.metadata.struct.Table;
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
        // 0. 表信息
        Table tableStruct = getTableStruct();
        
        // 1. sqlSource
        injectSqlSource(mappedStatement, "");
        
        // 2. resultMaps
        injectResultMaps(mappedStatement);
    }
    
}
