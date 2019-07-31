package in.hocg.squirrel.intercepts.pageable.builder;

import in.hocg.squirrel.utils.TextFormatter;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class OraclePageableBuilder implements PageableBuilder {
    
    @Override
    public String buildPageableSql(String sql, long offset, long size) {
        size = (offset >= 1) ? (offset + size) : size;
        String pageableSql = "SELECT * FROM (SELECT TMP.*, ROWNUM FROM ({sql}) TMP WHERE ROWNUM <= {offset}) WHERE ROWNUM > {size}";
        return TextFormatter.format(pageableSql, sql, offset, size);
    }
}
