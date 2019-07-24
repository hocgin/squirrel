package in.hocg.squirrel.provider;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * Created by hocgin on 2019-07-24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface BuildProvider {
    
    /**
     * 构建器
     * - Sql Source
     * - Result Map
     *
     * @param statement
     */
    void build(MappedStatement statement);
}
