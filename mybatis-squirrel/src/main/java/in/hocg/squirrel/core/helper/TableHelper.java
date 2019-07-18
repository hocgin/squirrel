package in.hocg.squirrel.core.helper;

import in.hocg.squirrel.core.annotation.Table;
import in.hocg.squirrel.core.table.ColumnStruct;
import in.hocg.squirrel.core.table.TableStruct;
import in.hocg.squirrel.exception.SquirrelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.impl.PerpetualCache;

import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class TableHelper {
    
    /**
     * 表结构 <实体类全名, TableStruct>
     */
    private static final Cache TABLE_CACHE = new SoftCache(new PerpetualCache("TABLE_CACHE"));
    
    /**
     * 列缓存 <实体类全名, ColumnStruct>
     */
    private static final Cache COLUMN_CACHE = new SoftCache(new PerpetualCache("COLUMN_CACHE"));
    
    /**
     * 获取表结构
     *
     * @param entityClass
     */
    public static List<ColumnStruct> getColumnStruct(Class<?> entityClass) {
        String entityClassName = entityClass.getName();
        Object columnStruct = COLUMN_CACHE.getObject(entityClassName);
        if (Objects.nonNull(columnStruct)) {
            return (List<ColumnStruct>) columnStruct;
        }
        TableStruct tableStruct = getTableStruct(entityClass);
        
        columnStruct = ColumnHelper.loadColumnStruct(tableStruct, entityClass);
        
        COLUMN_CACHE.putObject(entityClassName, columnStruct);
        
        return ((List<ColumnStruct>) columnStruct);
    }
    
    /**
     * 解析实体类获取表结构
     *
     * @param entityClass
     * @return
     */
    public static TableStruct getTableStruct(Class<?> entityClass) {
        String className = entityClass.getName();
        Object tableStruct = TABLE_CACHE.getObject(className);
        if (Objects.nonNull(tableStruct)) {
            return (TableStruct) tableStruct;
        }
    
        tableStruct = loadTableStruct(entityClass);
        TABLE_CACHE.putObject(className, tableStruct);
        return ((TableStruct) tableStruct);
    }
    
    /**
     * 加载实体的表结构
     *
     * @param entityClass
     * @return
     */
    private static TableStruct loadTableStruct(Class<?> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        if (Objects.isNull(table)) {
            throw SquirrelException.wrap("在 " + entityClass + " 未找到 @Table");
        }
    
        return new TableStruct()
                .setTableName(table.name());
    }
}
