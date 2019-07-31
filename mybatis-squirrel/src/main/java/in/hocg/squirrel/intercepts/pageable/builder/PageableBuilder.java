package in.hocg.squirrel.intercepts.pageable.builder;

import in.hocg.squirrel.utils.TextFormatter;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PageableBuilder {
    
    /**
     * 构建计数 SQL
     *
     * @param sql
     * @return
     */
    default String buildCountSql(String sql) {
        // fixme: 后续优化掉..
        return TextFormatter.format("SELECT COUNT(1) FROM ({sql}) t", sql);
    }
    
    /**
     * 构建分页 SQL
     *
     * @param sql
     * @param offset
     * @param size
     * @return
     */
    String buildPageableSql(String sql, long offset, long size);
}
