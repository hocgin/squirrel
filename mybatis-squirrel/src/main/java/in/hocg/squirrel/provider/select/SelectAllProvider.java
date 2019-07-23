package in.hocg.squirrel.provider.select;

import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.metadata.ColumnHelper;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.provider.BaseProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class SelectAllProvider extends BaseProvider {
    
    public SelectAllProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    public void selectAll(MappedStatement statement) {
        
        // 表信息
        Table tableStruct = getTableStruct();
        
        // 列信息
        List<Column> columnStruct = getColumnStruct();
        String[] columnsName = ColumnHelper.getColumnNames(columnStruct);
        
        String sql = XmlScripts.script(
                XmlScripts.select(tableStruct.getTableName(), columnsName)
        );
        
        // 设置 SQL
        injectSqlSource(statement, sql);
        
        // 设置结果
        injectResultMaps(statement);
        
    }
}
