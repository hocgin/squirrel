package in.hocg.squirrel.provider;

import com.google.common.collect.Lists;
import in.hocg.squirrel.core.StatementFields;
import in.hocg.squirrel.metadata.TableHelper;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 每个 @Provider 函数对应一个实例
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Data
public abstract class BaseProvider implements BuildProvider {
    
    /**
     * 代理函数名
     */
    public static final String PROVIDER_PROXY_METHOD = "method";
    
    /**
     * 构建函数名
     */
    public static final String PROVIDER_BUILD_METHOD = "build";
    
    /**
     * Mapper 类
     */
    private final Class<?> mapperClass;
    /**
     * Mapper 的实体
     */
    private final Class<?> entityClass;
    /**
     * 使用 Provider 的 Method
     */
    private final Method method;
    
    
    /**
     * MyBaits内部Xml节点解析的语言驱动
     */
    private static final XMLLanguageDriver LANG_DRIVER = new XMLLanguageDriver();
    
    /**
     * 表结构
     */
    private final Table tableStruct;
    /**
     * 列结构
     */
    private final List<Column> columnStruct;
    
    public BaseProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        this.mapperClass = mapperClass;
        this.entityClass = entityClass;
        this.method = method;
        this.tableStruct = TableHelper.getTableStruct(entityClass);
        this.columnStruct = TableHelper.getColumnStruct(entityClass);
    }
    
    public String method() {
        return "SQL";
    }
    
    /**
     * 调用 Provider 处理器构建函数
     *
     * @param statement
     */
    public void invokeProviderBuildMethod(MappedStatement statement) {
        
        try {
            Method method = this.getClass().getMethod(BaseProvider.PROVIDER_BUILD_METHOD, MappedStatement.class);
            method.invoke(this, statement);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("调用异常, Statement ID: {}, 函数名: {}, ERROR: {}", statement.getId(), BaseProvider.PROVIDER_BUILD_METHOD, e);
        }
    }
    
    
    /**
     * 设置 SqlSource 参数
     *
     * @param statement
     * @param sql
     */
    protected void setSqlSource(@NonNull MappedStatement statement, @NonNull String sql) {
        SqlSource sqlSource = LANG_DRIVER.createSqlSource(statement.getConfiguration(), sql.trim(), null);
        setSqlSource(statement, sqlSource);
    }
    
    /**
     * 设置 SqlSource 参数
     *
     * @param statement
     * @param sqlSource
     */
    protected void setSqlSource(MappedStatement statement, SqlSource sqlSource) {
        MetaObject metaObject = SystemMetaObject.forObject(statement);
        metaObject.setValue(StatementFields.SQL_SOURCE, sqlSource);
    }
    
    /**
     * 设置实体返回值
     *
     * @param statement
     */
    protected void setResultMaps(MappedStatement statement) {
        List<ResultMapping> resultMappings = Lists.newArrayList();
        for (Column column : columnStruct) {
            resultMappings.add(new ResultMapping.Builder(statement.getConfiguration(), column.getFieldName())
                    .column(column.getColumnName())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getJavaType())
                    .typeHandler(column.getTypeHandler())
                    .build());
        }
        String id = getStatementId(statement);
        
        ResultMap resultMap = new ResultMap.Builder(statement.getConfiguration(), id, this.entityClass, resultMappings, true)
                .build();
        SystemMetaObject.forObject(statement)
                .setValue(StatementFields.RESULT_MAPS, Collections.unmodifiableList(Arrays.asList(resultMap)));
    }
    
    /**
     * 设置指定类型的单返回值
     *
     * @param statement
     * @param singleClass
     */
    protected void setSingleResultMaps(MappedStatement statement, Class<?> singleClass) {
        String id = getStatementId(statement);
        ResultMap resultMap = new ResultMap.Builder(statement.getConfiguration(), id, singleClass, new ArrayList<>())
                .build();
        SystemMetaObject.forObject(statement)
                .setValue(StatementFields.RESULT_MAPS, Collections.unmodifiableList(Arrays.asList(resultMap)));
    }
    
    /**
     * 获取 MappedStatement Id
     *
     * @param statement
     * @return
     */
    private String getStatementId(MappedStatement statement) {
        return String.format("%s-Inline", statement.getId());
    }
    
    /**
     * 设置主键生成策略
     *
     * @param statement
     * @param keyProperties
     * @param keyColumnName
     * @param keyGenerator
     */
    protected void setKeyGenerator(MappedStatement statement,
                                   String keyProperties,
                                   String keyColumnName,
                                   KeyGenerator keyGenerator) {
        if (Objects.nonNull(keyGenerator)) {
            MetaObject metaObject = SystemMetaObject.forObject(statement);
            metaObject.setValue(StatementFields.KEY_PROPERTIES, new String[]{keyProperties});
            metaObject.setValue(StatementFields.KEY_COLUMNS, new String[]{keyColumnName});
            metaObject.setValue(StatementFields.KEY_GENERATOR, keyGenerator);
        }
    }
    
    
}
