package in.hocg.squirrel.metadata.struct;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.executor.keygen.KeyGenerator;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class Table {
    /**
     * 表名
     */
    private String tableName;
    
    /**
     * 主键名称
     */
    private String keyColumnName;
    
    /**
     * 主键字段
     */
    private String keyFieldName;
    
    /**
     * 主键生成策略
     */
    private KeyGenerator keyGenerator;
}
