package in.hocg.squirrel;

import com.google.common.collect.Sets;
import in.hocg.squirrel.helper.ProviderHelper;
import in.hocg.squirrel.intercepts.pageable.PageableInterceptor;
import in.hocg.squirrel.intercepts.typehandle.TypeHandleInterceptor;
import in.hocg.squirrel.provider.AbstractProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class MappedStatementSupport {
    
    /**
     * 已处理过的 MappedStatement
     */
    private static final Set<String> HANDLED_MAPPED_STATEMENT = Sets.newHashSet();
    
    /**
     * 处理 Mapper
     *
     * @param mappedStatements mappedStatements
     */
    public void handleMappedStatements(List<MappedStatement> mappedStatements) {
        handleMappedStatementMethods(Collections.unmodifiableList(mappedStatements));
    }
    
    /**
     * 处理标记 @XXProvider 映射的函数生成 MappedStatement
     */
    private void handleMappedStatementMethods(List<MappedStatement> mappedStatements) {
        for (MappedStatement statement : mappedStatements) {
            String statementId = statement.getId();
            if (HANDLED_MAPPED_STATEMENT.contains(statementId)) {
                continue;
            }
            
            // 如果是使用 @XXProvider
            if (statement.getSqlSource() instanceof ProviderSqlSource) {
                AbstractProvider provider = ProviderHelper.getMethodProvider(statementId);
                
                // 调用对应的 Provider 处理器，生成 MappedStatement 实例
                provider.buildMappedStatement(statement);
                
                HANDLED_MAPPED_STATEMENT.add(statementId);
            }
        }
    }
    
    /**
     * 装载插件
     *
     * @param configuration configuration
     */
    public void handleInterceptors(Configuration configuration) {
        // 分页插件
        configuration.addInterceptor(new PageableInterceptor());
        configuration.addInterceptor(new TypeHandleInterceptor());
    }
}
