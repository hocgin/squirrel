package in.hocg.squirrel.intercepts.pageable.builder;

import com.google.common.collect.Maps;
import in.hocg.squirrel.core.Dialect;
import in.hocg.squirrel.exception.SquirrelException;

import java.util.Map;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class PageableBuilderFactory {
    
    private static final Map<Dialect, PageableBuilder> CACHE = Maps.newHashMap();
    
    /**
     * 获取分页SQL构建器
     *
     * @param dialect
     * @return
     */
    public static PageableBuilder getPageableBuilder(Dialect dialect) {
        return CACHE.computeIfAbsent(dialect, d -> {
            switch (d) {
                case Oracle: {
                    return new OraclePageableBuilder();
                }
                case MySQL:
                case MariaDB: {
                    return new MySqlPageableBuilder();
                }
                case Unknown:
                default: {
                    throw SquirrelException.wrap("所使用的数据库暂不被支持");
                }
            }
        });
    }
}
