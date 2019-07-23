package in.hocg.squirrel.provider.delete;

import in.hocg.squirrel.builder.SqlScripts;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.provider.BaseProvider;
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
public class DeleteOneProvider extends BaseProvider {
    
    public DeleteOneProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    public void deleteOne(MappedStatement statement) {
        // 表
        Table tableStruct = getTableStruct();
        
        String sql = new SQL()
                .DELETE_FROM(tableStruct.getTableName())
                .WHERE(SqlScripts.idEq(tableStruct.getKeyFieldName()))
                .toString();
        
        injectSqlSource(statement, sql);
    }
}
