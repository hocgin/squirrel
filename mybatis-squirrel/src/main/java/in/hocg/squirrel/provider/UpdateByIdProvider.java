package in.hocg.squirrel.provider;

import in.hocg.squirrel.builder.SqlKeyword;
import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.utils.TextFormatter;
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
public class UpdateByIdProvider extends AbstractProvider {
    
    public UpdateByIdProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    @Override
    public void build(MappedStatement statement) {
        
        Table table = getTable();
        
        String sql = XmlScripts.script(
                XmlScripts.update(table.getTableName()),
                XmlScripts.set(getSets()),
                XmlScripts.where(
                        XmlScripts.eq(table.getKeyColumnName(), Constants.BEAN_PARAMETER + Constants.COMMA + Constants.KEY_PARAMETER)
                )
        );
        
        setSqlSource(statement, sql);
    }
    
    /**
     * xx = xx
     *
     * @return
     */
    private String[] getSets() {
        // 列信息
        List<Column> columnStruct = getColumns();
        return columnStruct.stream()
                .filter(column -> !column.getIsPk())
                .map(column -> TextFormatter.format("{column} = {field}", column.getColumnName(), Constants.BEAN_PARAMETER_PREFIX + column.getEl() + Constants.PARAMETER_SUFFIX) + SqlKeyword.SPLIT.getValue())
                .toArray(String[]::new);
    }
}
