package in.hocg.squirrel.utils;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author hocgin
 * @date 2019/7/19
 */
public class MessageFormatter {
    
    
    public static String format(String format) {
        return arrayFormat(format, new Object[]{});
    }
    
    public static String format(String format, Object arg1) {
        return arrayFormat(format, new Object[]{arg1});
    }
    
    public static String format(String format, Object arg1, Object... args) {
        List<Object> allArg = Arrays.asList(arg1, args);
        return arrayFormat(format, allArg.toArray());
    }
    
    /**
     * 字符串占位符 {} 替换
     * 例如: "我是 {}", "HOCGIN"  ==> "我是 HOCGIN"
     * 例如: "我是 {name}", "HOCGIN"  ==> "我是 HOCGIN"
     *
     * @param messagePattern
     * @param args
     * @return
     */
    public static String arrayFormat(final String messagePattern, Object[] args) {
        if (Objects.isNull(messagePattern)) {
            throw new IllegalArgumentException("messagePattern 参数不能为 NULL");
        }
        
        if (Objects.isNull(args)) {
            throw new IllegalArgumentException("args 参数不能为 NULL");
        }
        
        String sbuf = messagePattern;
        if (Strings.isNotBlank(messagePattern)) {
            for (Object arg : args) {
                sbuf = sbuf.replaceFirst("\\{.*?}", Objects.isNull(arg) ? "null" : arg.toString());
            }
        }
        return sbuf;
    }

}
