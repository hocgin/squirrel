package in.hocg.squirrel.core.table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class TableStruct {
    /**
     * 表名
     */
    private String tableName;
    
    /**
     * 主键名称
     */
    private String idColumnName;
    
    /**
     * 主键字段
     */
    private String idFiledName;
}
