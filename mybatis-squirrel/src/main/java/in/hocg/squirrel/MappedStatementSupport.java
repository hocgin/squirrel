package in.hocg.squirrel;

import in.hocg.squirrel.core.helper.ProviderHelper;
import in.hocg.squirrel.core.helper.StatementHelper;
import in.hocg.squirrel.provider.BaseProvider;
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
        handleProviderMethod();
    }
    
    /**
     * 处理标记 @XXProvider 映射的函数生成 MappedStatement
     */
    private void handleProviderMethod() {
        Collection<MappedStatement> mappedStatements = StatementHelper.getMappedStatement();
        for (MappedStatement mappedStatement : mappedStatements) {
            String mappedStatementId = mappedStatement.getId();
            if (!StatementHelper.isLoadedMappedStatement(mappedStatementId)
                    && (mappedStatement.getSqlSource() instanceof ProviderSqlSource)) {
                BaseProvider provider = ProviderHelper.getMethodProvider(mappedStatementId);
                
                // 调用对应的 Provider 处理器，生成 MappedStatement 实例
                provider.invokeProviderMethod(mappedStatement);
                
                // 标记为已加载
                StatementHelper.setLoadedMappedStatement(mappedStatementId);
            }
        }
    }
}
