package in.hocg.squirrel.provider;

import in.hocg.squirrel.builder.SqlScripts;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.provider.AbstractProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class DeleteOneProvider extends AbstractProvider {
    
    public DeleteOneProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    @Override
    public void build(MappedStatement statement) {
        Table table = getTable();
        
        String sql = new SQL()
                .DELETE_FROM(table.getTableName())
                .WHERE(SqlScripts.idEq(table.getKeyFieldName()))
                .toString();
        
        setSqlSource(statement, sql);
    }
}
