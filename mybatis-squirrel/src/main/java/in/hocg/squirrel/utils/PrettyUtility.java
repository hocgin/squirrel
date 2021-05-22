package in.hocg.squirrel.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;

import java.util.StringTokenizer;

/**
 * Created by hocgin on 2019-07-28.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@UtilityClass
public class PrettyUtility {


    /**
     * 美化 SQL
     * @param boundSql
     * @return r
     */
    public static String sql(BoundSql boundSql) {
        // fixme: 等待补充美化SQL..
        String sql = removeBreakingWhitespace(boundSql.getSql());
        return sql.replaceAll("[\\s]+", " ");
    }

    /**
     * 移除多的空格
     *
     * @param original
     * @return r
     */
    public static String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        while (whitespaceStripper.hasMoreTokens()) {
            builder.append(whitespaceStripper.nextToken());
            builder.append(" ");
        }
        return builder.toString();
    }
}
