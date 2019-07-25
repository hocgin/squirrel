package in.hocg.squirrel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by hocgin on 2019-07-22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
class TextFormatterTest {
    
    @Test
    void format() {
        Assertions.assertEquals("P1} {10}", TextFormatter.format("P{i} {}", "1}", "{10}", "12"));
        Assertions.assertEquals("P{i} {}", TextFormatter.format("P{i} {}"));
        Assertions.assertEquals("P1} {}", TextFormatter.format("P{i} {}", "1}"));
    }
}