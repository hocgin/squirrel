package in.hocg.squirrel.provider.methods;

import in.hocg.squirrel.metadata.ColumnUtility;
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
        
        Table table = getTable();
        
        List<Column> columns = getColumns();
        String[] columnNames = ColumnUtility.getColumnNames(columns);
        String[] columnParameters = ColumnUtility.getColumnParameters(columns);
        
        String sql = new SQL()
                .INSERT_INTO(table.getTableName())
                .INTO_COLUMNS(columnNames)
                .INTO_VALUES(columnParameters)
                .toString();
        
        setSqlSource(statement, sql);
        
        setKeyGenerator(statement, table.getKeyFieldName(), table.getKeyColumnName(), table.getKeyGenerator());
    }
}
