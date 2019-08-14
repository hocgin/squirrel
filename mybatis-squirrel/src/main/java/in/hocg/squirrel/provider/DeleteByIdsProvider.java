package in.hocg.squirrel.provider;

import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.metadata.struct.Table;
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
public class DeleteByIdsProvider extends AbstractProvider {
    
    public DeleteByIdsProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    @Override
    public void build(MappedStatement statement) {
        Table table = getTable();
    
        String sql = XmlScripts.script(
                XmlScripts.delete(table.getTableName()),
                XmlScripts.where(
                        XmlScripts.in(table.getKeyColumnName(), Constants.ARRAY_PARAMETER, Constants.KEY_PARAMETER)
                )
        );
        
        setSqlSource(statement, sql);
    }
}
