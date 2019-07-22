package in.hocg.squirrel.provider;

import com.google.common.collect.Lists;
import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.core.helper.StatementHelper;
import in.hocg.squirrel.metadata.TableHelper;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.utils.LangKit;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 每个 @Provider 函数对应一个实例
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Data
public abstract class BaseProvider {
    
    public static final String PROVIDER_METHOD = "method";
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
    private static final XMLLanguageDriver langDriver = new XMLLanguageDriver();
    
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
        log.debug("");
    }
    
    public String method() {
        return "SQL";
    }
    
    /**
     * 调用Provider处理器指定函数名的函数
     *
     * @param mappedStatement
     */
    public void invokeProviderMethod(MappedStatement mappedStatement) {
        String methodName = StatementHelper.getMethodName(mappedStatement.getId());
        invokeProviderMethod(methodName, mappedStatement);
    }
    
    /**
     * 调用Provider处理器指定函数名的函数
     *
     * @param methodName
     * @param mappedStatement
     */
    private void invokeProviderMethod(String methodName, MappedStatement mappedStatement) {
        try {
            Method method = this.getClass().getMethod(methodName, MappedStatement.class);
            method.invoke(this, mappedStatement);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("调用异常, Statement ID: {}, 函数名: {}, ERROR: {}", mappedStatement.getId(), methodName, e);
        }
    }
    
    
    /**
     * 设置 SqlSource 参数
     *
     * @param statement
     * @param sql
     */
    protected void injectSqlSource(@NonNull MappedStatement statement, @NonNull String sql) {
        SqlSource sqlSource = langDriver.createSqlSource(statement.getConfiguration(), sql.trim(), null);
        injectSqlSource(statement, sqlSource);
    }
    
    /**
     * 设置 SqlSource 参数
     *
     * @param statement
     * @param sqlSource
     */
    protected void injectSqlSource(MappedStatement statement, SqlSource sqlSource) {
        MetaObject metaObject = SystemMetaObject.forObject(statement);
        metaObject.setValue(Constants.MAPPED_STATEMENT_FIELD__SQL_SOURCE, sqlSource);
    }
    
    /**
     * 设置 ResultMap 参数
     *
     * @param statement
     */
    protected void injectResultMaps(MappedStatement statement) {
        List<ResultMapping> resultMappings = Lists.newArrayList();
        for (Column column : columnStruct) {
            resultMappings.add(new ResultMapping.Builder(statement.getConfiguration(), column.getFieldName())
                    .column(column.getColumnName())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getJavaType())
                    .typeHandler(column.getTypeHandler())
                    .build());
        }
        String id = String.format("%s-Inline", statement.getId());
        
        ResultMap resultMap = new ResultMap.Builder(statement.getConfiguration(), id, this.entityClass, resultMappings, true)
                .build();
        SystemMetaObject.forObject(statement).setValue("resultMaps", Collections.unmodifiableList(Arrays.asList(resultMap)));
    }
    
    /**
     * 获取所有列名
     * @return
     */
    protected String[] getColumnsName() {
        if (LangKit.isEmpty(this.columnStruct)) {
            return new String[]{};
        }
        return this.columnStruct.stream().map(Column::getColumnName).toArray(String[]::new);
    }
}
