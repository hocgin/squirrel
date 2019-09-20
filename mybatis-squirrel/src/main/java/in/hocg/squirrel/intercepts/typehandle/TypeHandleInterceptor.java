package in.hocg.squirrel.intercepts.typehandle;

import com.google.common.collect.Lists;
import in.hocg.squirrel.constant.MappedStatementFields;
import in.hocg.squirrel.intercepts.AbstractInterceptor;
import in.hocg.squirrel.utils.ClassUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static in.hocg.squirrel.helper.StatementHelper.getStatementId;

/**
 * Created by hocgin on 2019-09-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
        }),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class
        })
})
public class TypeHandleInterceptor extends AbstractInterceptor {
    
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        List<ResultMap> resultMaps = ms.getResultMaps();
        List<ResultMap> newResultMaps = Lists.newArrayList();
        for (ResultMap resultMap : resultMaps) {
            Class<?> type = resultMap.getType();
            newResultMaps.add(getResultMap(ms, type));
        }
    
        SystemMetaObject.forObject(ms)
                .setValue(MappedStatementFields.RESULT_MAPS, Collections.unmodifiableList(newResultMaps));
        return invocation.proceed();
    }
    
    private ResultMap getResultMap(MappedStatement statement, Class<?> clazz) {
        List<ResultMapping> resultMappings = Lists.newArrayList();
        List<Field> fields = ClassUtility.from(clazz).getAllField();
        for (Field field : fields) {
            resultMappings.add(getResultMapping(field, statement.getConfiguration()));
        }
        
        return new ResultMap.Builder(statement.getConfiguration(),
                getStatementId(statement),
                clazz,
                resultMappings,
                true).build();
    }
    
    private boolean useTypeHandle(Class<?> type) {
        return type.isAnnotationPresent(UseTypeHandle.class);
    }
    
    private ResultMapping getResultMapping(Field field, final Configuration configuration) {
        String fieldName = field.getName();
        String columnName = fieldName;
        Class<?> javaType = field.getType();
        JdbcType jdbcType = JdbcType.UNDEFINED;
        Class<? extends TypeHandler<?>> typeHandler = UnknownTypeHandler.class;
        
        CustomTypeHandle annotation = field.getAnnotation(CustomTypeHandle.class);
        if (Objects.nonNull(annotation)) {
            typeHandler = annotation.typeHandler();
        }
        
        ResultMapping.Builder builder = new ResultMapping.Builder(configuration, fieldName,
                columnName, javaType).jdbcType(jdbcType);
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
        if (typeHandler != UnknownTypeHandler.class) {
            TypeHandler<?> _typeHandler = registry.getMappingTypeHandler(typeHandler);
            if (_typeHandler == null) {
                _typeHandler = registry.getInstance(javaType, typeHandler);
            }
            builder.typeHandler(_typeHandler);
        }
        return builder.build();
    }
}
