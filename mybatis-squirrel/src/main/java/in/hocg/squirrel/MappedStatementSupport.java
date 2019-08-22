package in.hocg.squirrel;

import in.hocg.squirrel.helper.ProviderHelper;
import in.hocg.squirrel.helper.MappedStatementHelper;
import in.hocg.squirrel.intercepts.pageable.PageableInterceptor;
import in.hocg.squirrel.provider.AbstractProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.util.Collection;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class MappedStatementSupport {
    
    /**
     * 处理 Mapper
     *
     * @param mappedStatements mappedStatements
     */
    public void handleMapper(Collection<MappedStatement> mappedStatements) {
        for (MappedStatement statement : mappedStatements) {
            MappedStatementHelper.addMappedStatement(statement);
        }
        handleMappedStatementMethods();
    }
    
    /**
     * 处理标记 @XXProvider 映射的函数生成 MappedStatement
     */
    private void handleMappedStatementMethods() {
        Collection<MappedStatement> mappedStatements = MappedStatementHelper.getMappedStatement();
        for (MappedStatement statement : mappedStatements) {
            String statementId = statement.getId();
            if (MappedStatementHelper.isBuiltMappedStatement(statementId)) {
                continue;
            }
            
            // 如果是使用 @XXProvider
            if (statement.getSqlSource() instanceof ProviderSqlSource) {
                AbstractProvider provider = ProviderHelper.getMethodProvider(statementId);
                
                // 调用对应的 Provider 处理器，生成 MappedStatement 实例
                provider.invokeProviderBuildMethod(statement);
                
                // 标记为已加载
                MappedStatementHelper.addBuiltMappedStatement(statementId);
            }
        }
    }
    
    /**
     * 装载插件
     * @param configuration configuration
     */
    public void handleInterceptors(Configuration configuration) {
        // 分页插件
        configuration.addInterceptor(new PageableInterceptor());
    }
}
