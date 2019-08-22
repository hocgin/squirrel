package in.hocg.squirrel.helper;

import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.exception.SquirrelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Objects;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class MappedStatementHelper {
    
    /**
     * Mapper Class 缓存
     */
    private static Cache MAPPER_CLASS_CACHE = new SoftCache(new PerpetualCache("MAPPER_CLASS_CACHE"));
    
    /**
     * 解析 statementId 获取函数名
     *
     * @param statementId
     * @return
     */
    public static String getMethodName(String statementId) {
        if (isStatementId(statementId)) {
            throw SquirrelException.wrap("StatementId: {} 格式错误", statementId);
        }
        return statementId.substring(statementId.lastIndexOf(Constants.COMMA) + 1);
    }
    
    /**
     * 通过 statement id 获取对应的 Mapper 类
     *
     * @param statementId statement id
     * @return 对应的 Mapper 类
     */
    public static Class<?> getMapperClass(String statementId) {
        if (isStatementId(statementId)) {
            throw SquirrelException.wrap("Statement Id: {} 格式错误", statementId);
        }
        String mapperClassName = statementId.substring(0, statementId.lastIndexOf(Constants.COMMA));
        
        Class<?> mapperClass = ((Class<?>) MAPPER_CLASS_CACHE.getObject(statementId));
        if (Objects.nonNull(mapperClass)) {
            return mapperClass;
        }
        
        try {
            // 从资源中获取匹配的 mapper 类
            mapperClass = Resources.classForName(mapperClassName);
        } catch (ClassNotFoundException e) {
            log.error("{} 类通过类加载器没有找到", mapperClassName);
            throw SquirrelException.wrap("从资源中获取 Mapper 类出错 {}", e.getMessage());
        }
        
        MAPPER_CLASS_CACHE.putObject(mapperClassName, mapperClass);
        return mapperClass;
    }
    
    /**
     * @param statement
     * @return
     */
    public static String getInlineStatementId(MappedStatement statement) {
        return String.format("%s-Inline", statement.getId());
    }
    
    /**
     * 判断 statement id 是否符合
     *
     * @param statementId statement id
     * @return 是否符合
     */
    public static boolean isStatementId(String statementId) {
        return Objects.isNull(statementId) || !statementId.contains(Constants.COMMA);
    }
}
