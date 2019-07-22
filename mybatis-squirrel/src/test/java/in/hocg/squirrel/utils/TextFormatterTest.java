package in.hocg.squirrel.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * Created by hocgin on 2019-07-22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
class TextFormatterTest {
    
    @Test
    void format() {
        Assert.isTrue("P1} {10}".equals(TextFormatter.format("P{i} {}", "1}", "{10}", "12")), "错误");
        Assert.isTrue("P{i} {}".equals(TextFormatter.format("P{i} {}")), "错误");
        Assert.isTrue("P1} {}".equals(TextFormatter.format("P{i} {}", "1}")), "错误");
    }
}