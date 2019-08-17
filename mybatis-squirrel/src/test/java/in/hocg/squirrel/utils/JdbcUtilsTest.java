package in.hocg.squirrel.utils;

import in.hocg.squirrel.core.Dialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
class JdbcUtilsTest {
    
    @Test
    void getDialect() {
        Dialect dialect = JdbcUtility.getDialect("jdbc:mariadb://mysql.localhost:3306/db_test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
        Assertions.assertEquals(dialect, Dialect.MariaDB);
    }
}