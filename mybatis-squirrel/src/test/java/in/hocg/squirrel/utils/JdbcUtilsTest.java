package in.hocg.squirrel.utils;

import in.hocg.squirrel.core.Dialect;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
class JdbcUtilsTest {
    
    @Test
    void getDialect() {
        Dialect dialect = JdbcUtils.getDialect("jdbc:mariadb://mysql.localhost:3306/db_test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
        Assert.isTrue(dialect.equals(Dialect.MariaDB));
    }
}