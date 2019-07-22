package in.hocg.squirrel.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hocgin
 * @date 2019/7/19
 */
@Slf4j
public class TextFormatter {
    private static final Pattern OPEN_CLOSE = Pattern.compile("\\{.*?}");
    
    public static String format(String format) {
        return format;
    }
    
    public static String format(String format, Object arg1) {
        return arrayFormat(format, new Object[]{arg1});
    }
    
    public static String format(String format, Object arg1, Object... args) {
        ArrayList<Object> allArg = new ArrayList<>(args.length + 1);
        allArg.add(arg1);
        allArg.addAll(Arrays.asList(args));
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
    private static String arrayFormat(@NonNull final String messagePattern, @NonNull Object[] args) {
        StringBuffer sb = new StringBuffer();
        if (Strings.isNotBlank(messagePattern)) {
            Matcher matcher = OPEN_CLOSE.matcher(messagePattern);
            int i = 0;
            while (matcher.find()) {
                if (args.length < (i + 1)) {
                    break;
                }
                matcher.appendReplacement(sb, Objects.toString(args[i++]));
            }
            matcher.appendTail(sb);
        }
        return sb.toString();
    }
    
}
