package in.hocg.squirrel;

import in.hocg.squirrel.helper.ProviderHelper;
import in.hocg.squirrel.helper.StatementHelper;
import in.hocg.squirrel.provider.AbstractProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;

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
     * 生成 MappedStatement
     *
     * @param mappedStatements
     */
    public void support(Collection<Object> mappedStatements) {
        for (Object mappedStatement : mappedStatements) {
            if (!(mappedStatement instanceof MappedStatement)) {
                continue;
            }
            MappedStatement statement = (MappedStatement) mappedStatement;
            
            // 缓存
            StatementHelper.addMappedStatement(statement);
        }
        // ..
        handleMappedStatementMethods();
    }
    
    /**
     * 处理标记 @XXProvider 映射的函数生成 MappedStatement
     */
    private void handleMappedStatementMethods() {
        Collection<MappedStatement> mappedStatements = StatementHelper.getMappedStatement();
        for (MappedStatement statement : mappedStatements) {
            String statementId = statement.getId();
            if (StatementHelper.isBuiltMappedStatement(statementId)) {
                continue;
            }
            
            // 如果是使用 @XXProvider
            if (statement.getSqlSource() instanceof ProviderSqlSource) {
                AbstractProvider provider = ProviderHelper.getMethodProvider(statementId);
                
                // 调用对应的 Provider 处理器，生成 MappedStatement 实例
                provider.invokeProviderBuildMethod(statement);
                
                // 标记为已加载
                StatementHelper.addBuiltMappedStatement(statementId);
            }
        }
    }
}
