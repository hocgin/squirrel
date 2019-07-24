package in.hocg.squirrel.provider.methods;

import in.hocg.squirrel.builder.XmlScripts;
import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.provider.AbstractProvider;
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
public class UpdateOneProvider extends AbstractProvider {
    
    public UpdateOneProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        super(mapperClass, entityClass, method);
    }
    
    @Override
    public void build(MappedStatement statement) {
        // 表信息
        Table tableStruct = getTableStruct();
        
        String sql = XmlScripts.script(
                XmlScripts.update(tableStruct.getTableName()),
                XmlScripts.set(getSets()),
                XmlScripts.where(
                        XmlScripts.eq(tableStruct.getKeyColumnName(), Constants.BEAN_PARAMETER + Constants.COMMA + Constants.KEY_PARAMETER)
                )
        );
        
        // 设置 SQL
        setSqlSource(statement, sql);
    }
    
    /**
     * xx = xx
     *
     * @return
     */
    private String[] getSets() {
        // 列信息
        List<Column> columnStruct = getColumnStruct();
        return columnStruct.stream()
                .filter(column -> !column.getIsPk())
                .map(column -> TextFormatter.format("{column} = {field}", column.getColumnName(), Constants.BEAN_PARAMETER_PREFIX + column.getFieldName()) + Constants.PARAMETER_SUFFIX)
                .toArray(String[]::new);
    }
}
