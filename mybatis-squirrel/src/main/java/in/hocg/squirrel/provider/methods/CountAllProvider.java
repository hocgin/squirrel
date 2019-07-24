package in.hocg.squirrel.provider.methods;

import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.provider.AbstractProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * Created by hocgin on 2019-07-23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class CountAllProvider extends AbstractProvider {
    
    public CountAllProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    @Override
    public void build(MappedStatement statement) {
        // 表信息
        Table tableStruct = getTableStruct();
        
        String sql = XmlScripts.script(
                XmlScripts.count(tableStruct.getTableName(), tableStruct.getKeyColumnName())
        );
        
        // 设置SQL
        setSqlSource(statement, sql);
        
        // 设置返回值
        setResultMaps(statement, Long.class);
    }
}
