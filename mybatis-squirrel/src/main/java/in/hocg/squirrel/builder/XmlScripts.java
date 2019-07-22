package in.hocg.squirrel.builder;

import com.google.common.collect.Maps;
import in.hocg.squirrel.utils.TextFormatter;
import lombok.NonNull;
import org.apache.ibatis.jdbc.SQL;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

/**
 * Created by hocgin on 2019-07-22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class XmlScripts {
    /**
     * 参数前缀
     */
    private static final String PARAMETER_PREFIX = "#{";
    /**
     * 参数后缀
     */
    private static final String PARAMETER_SUFFIX = "}";
    private static final String SCRIPT = "script";
    private static final String IF = "if";
    private static final String WHERE = "where";
    private static final String SET = "set";
    private static final String FOREACH = "foreach";
    
    private static final HashMap<String, String> EMPTY = Maps.newHashMap();
    
    /**
     * <script/>
     *
     * @param inner
     * @return
     */
    public static String script(String... inner) {
        return node(SCRIPT, inner);
    }
    
    /**
     * <if/>
     *
     * @param test
     * @param inner
     * @return
     */
    public static String _if(@NonNull String test, String... inner) {
        HashMap<String, String> attrs = Maps.newHashMap();
        attrs.put("test", test);
        return node(IF, attrs, inner);
    }
    
    /**
     * <if test="#{xx} != null"/>
     *
     * @param param
     * @param inner
     * @return
     */
    public static String ifNotNull(@NonNull String param, String... inner) {
        return _if(TextFormatter.format("'{param} != null'", param), inner);
    }
    
    /**
     * <foreach/>
     *
     * @param collection
     * @param item
     * @param separator
     * @param open
     * @param close
     * @return
     */
    public static String foreach(@NonNull String collection,
                                 @NonNull String item,
                                 @NonNull String separator,
                                 String open,
                                 String close) {
        HashMap<String, String> attrs = Maps.newHashMap();
        attrs.put("collection", collection);
        attrs.put("item", item);
        attrs.put("separator", separator);
        
        if (Strings.isNotBlank(open)) {
            attrs.put("open", open);
        }
        if (Strings.isNotBlank(close)) {
            attrs.put("close", close);
        }
        String inner = String.format("%s%s%s", PARAMETER_PREFIX, item, PARAMETER_SUFFIX);
        return node(FOREACH, attrs, inner);
    }
    
    /**
     * <where/>
     *
     * @param inner
     * @return
     */
    public static String where(String... inner) {
        return node(WHERE, EMPTY, inner);
    }
    
    /**
     * <set/>
     *
     * @param inners
     * @return
     */
    public static String set(String... inners) {
        Optional<String> setOptional = Arrays.stream(inners)
                .reduce((s, s2) -> s + SqlKeyword.SPLIT.getValue() + s2);
        return node(SET, EMPTY, setOptional.orElse(""));
    }
    
    public static String node(String tag, String... inner) {
        return node(tag, tag, EMPTY, inner);
    }
    
    public static String node(String tag,
                              Map<String, String> attrs,
                              String... inner) {
        return node(tag, tag, attrs, inner);
    }
    
    /**
     * 构建一个 xml 节点
     *
     * @param openTag
     * @param closeTag
     * @param attrs
     * @param inner
     * @return
     */
    public static String node(String openTag,
                              String closeTag,
                              Map<String, String> attrs,
                              String... inner) {
        final String nodeStr = "<{tag}{allAttr}>{allInner}</{tag}>";
        
        String allAttrs = "";
        if (Objects.nonNull(attrs) && attrs.size() > 0) {
            allAttrs = " " + attrs.keySet().stream()
                    .map((k) -> String.format("%s=\"%s\"", k, attrs.get(k)))
                    .reduce((s, s2) -> s + " " + s2).orElse("");
        }
        String allInner = "";
        if (Objects.nonNull(inner) && inner.length > 0) {
            allInner = Arrays.stream(inner)
                    .reduce((s, s2) -> s + s2)
                    .orElse("");
        }
        return TextFormatter.format(nodeStr, openTag, allAttrs, allInner, closeTag);
    }
    
    public static String select(String tableName, String[] columns) {
        return new SQL().SELECT(columns).FROM(tableName).toString();
    }
    
    /**
     * field = #{v}
     *
     * @param keyColumnName
     * @param fieldName
     * @return
     */
    public static String eq(@NonNull String keyColumnName,
                            @NonNull String fieldName) {
        return keyColumnName +
                SqlKeyword.EQ.getValue() +
                PARAMETER_PREFIX +
                fieldName +
                PARAMETER_SUFFIX;
    }
    
    /**
     * sql
     *
     * @param sql
     * @return
     */
    public static String sql(@NonNull String sql) {
        return sql;
    }
}
