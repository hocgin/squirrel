package in.hocg.squirrel.intercepts.pageable.builder;

import in.hocg.squirrel.utils.TextFormatter;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class MySqlPageableBuilder implements PageableBuilder {
    
    @Override
    public String buildPageableSql(String sql, int offset, int size) {
        return TextFormatter.format("{sql}\nLIMIT {offset}, {size}", sql, offset, size);
    }
}
