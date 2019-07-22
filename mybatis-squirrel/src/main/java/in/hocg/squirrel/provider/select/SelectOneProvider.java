package in.hocg.squirrel.provider.select;

import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.core.Constants;
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
        // 1. 表信息
        Table tableStruct = getTableStruct();
        // 2. 列信息
        String[] columnsName = getColumnsName();
    
        String sql = XmlScripts.script(
                XmlScripts.select(tableStruct.getTableName(), columnsName),
                XmlScripts.where(
                        XmlScripts.eq(tableStruct.getKeyColumnName(), Constants.KEY_COLUMN_PARAM)
                )
        );
        
        // 3. sqlSource
        injectSqlSource(mappedStatement, sql);
        
        // 4. resultMaps
        injectResultMaps(mappedStatement);
    }
    
}
