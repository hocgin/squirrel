package in.hocg.squirrel.utils;

import in.hocg.squirrel.core.Dialect;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class JdbcUtility {
    private static final Pattern PATTERN_JDBC_TYPE = Pattern.compile(":.*?:");
    
    /**
     * 根据 URL 来获取数据库类型
     * @param jdbcUrl
     * @return
     */
    public Dialect getDialect(@NonNull String jdbcUrl) {
        Matcher matcher = PATTERN_JDBC_TYPE.matcher(jdbcUrl);
        if (!matcher.find()) {
            return Dialect.Unknown;
        }
    
        return Dialect.of(matcher.group(0), Dialect.Unknown);
    }
    
}
