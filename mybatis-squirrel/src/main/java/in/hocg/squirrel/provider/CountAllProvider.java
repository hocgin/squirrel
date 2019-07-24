package in.hocg.squirrel.provider;

import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.metadata.struct.Table;
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
        Table table = getTable();
        
        String sql = XmlScripts.script(
                XmlScripts.count(table.getTableName(), table.getKeyColumnName())
        );
        
        setSqlSource(statement, sql);
        
        setResultMaps(statement, Long.class);
    }
}
