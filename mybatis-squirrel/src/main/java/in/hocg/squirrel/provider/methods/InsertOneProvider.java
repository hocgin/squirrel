package in.hocg.squirrel.provider.methods;

import in.hocg.squirrel.metadata.ColumnHelper;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.provider.AbstractProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
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
public class InsertOneProvider extends AbstractProvider {
    
    public InsertOneProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    @Override
    public void build(MappedStatement statement) {
        // 表
        Table tableStruct = getTableStruct();
        
        // 列
        List<Column> columnStruct = getColumnStruct();
        String[] columnsName = ColumnHelper.getColumnNames(columnStruct);
        String[] columnParameters = ColumnHelper.getColumnParameters(columnStruct);
        
        // sql
        String sql = new SQL()
                .INSERT_INTO(tableStruct.getTableName())
                .INTO_COLUMNS(columnsName)
                .INTO_VALUES(columnParameters)
                .toString();
        
        // SQLSource
        setSqlSource(statement, sql);
        
        // 设置主键生成策略
        setKeyGenerator(statement, tableStruct.getKeyFieldName(), tableStruct.getKeyColumnName(), tableStruct.getKeyGenerator());
        
    }
}
