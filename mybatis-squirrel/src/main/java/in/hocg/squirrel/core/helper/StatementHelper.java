package in.hocg.squirrel.core.helper;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.exception.SquirrelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class StatementHelper {
    /**
     * 缓存 <statementId, MappedStatement>
     */
    private static final Map<String, MappedStatement> CACHE = Maps.newHashMap();
    /**
     * 已加载标识 <statementId>
     */
    private static final Set<String> LOADED_MAPPED_STATEMENT = Sets.newHashSet();
    /**
     * Mapper Class 缓存
     */
    private static Cache MAPPER_CLASS_CACHE = new SoftCache(new PerpetualCache("MAPPER_CLASS_CACHE"));
    
    
    public static void setLoadedMappedStatement(String statementId) {
        LOADED_MAPPED_STATEMENT.add(statementId);
    }
    
    public static boolean isLoadedMappedStatement(String statementId) {
        return LOADED_MAPPED_STATEMENT.contains(statementId);
    }
    
    public static void addMappedStatement(MappedStatement statement) {
        CACHE.put(statement.getId(), statement);
    }
    
    public static Collection<MappedStatement> getMappedStatement() {
        return CACHE.values();
    }
    
    /**
     * 解析 statementId 获取函数名
     *
     * @param statementId
     * @return
     */
    public static String getMethodName(String statementId) {
        if (!statementId.contains(Constants.COMMA)) {
            log.error("Statement Id = {}，不包含\".\"", statementId);
            throw SquirrelException.wrap("StatementId:{} 错误", statementId);
        }
        return statementId.substring(statementId.lastIndexOf(Constants.COMMA) + 1);
    }
    
    /**
     * 获取 Mapper 类
     *
     * @param statementId
     * @return
     */
    public static Class<?> getMapperClass(String statementId) {
        if (!statementId.contains(Constants.COMMA)) {
            log.error("Statement Id = {}，不包含.", statementId);
            throw SquirrelException.wrap("StatementId:{} 错误", statementId);
        }
        String mapperClassName = statementId.substring(0, statementId.lastIndexOf(Constants.COMMA));
        
        Class<?> mapperClass = ((Class<?>) MAPPER_CLASS_CACHE.getObject(statementId));
        if (Objects.nonNull(mapperClass)) {
            return mapperClass;
        }
        
        try {
            mapperClass = Resources.classForName(mapperClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if (Objects.isNull(mapperClass)) {
            log.error("{} 类通过类加载器没有找到", mapperClassName);
            throw SquirrelException.wrap("{} 类没有找到", mapperClassName);
        }
        MAPPER_CLASS_CACHE.putObject(mapperClassName, mapperClass);
        return mapperClass;
    }
}
