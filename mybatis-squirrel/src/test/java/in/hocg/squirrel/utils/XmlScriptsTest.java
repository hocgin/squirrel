package in.hocg.squirrel.utils;

import in.hocg.squirrel.builder.XmlScripts;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * Created by hocgin on 2019-07-22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
class XmlScriptsTest {
    
    @Test
    void node() {
        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("test", "id != null && id != ''");
        String anIf = XmlScripts.node("if", attrs, "A.ID >= #{id}");
        Assert.isTrue("".equals(""), "错误");
    }
}