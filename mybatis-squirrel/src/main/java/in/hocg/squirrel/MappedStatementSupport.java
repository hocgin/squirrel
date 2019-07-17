package in.hocg.squirrel;

import in.hocg.squirrel.core.helper.MappedStatementHelper;
import in.hocg.squirrel.core.helper.ProviderHelper;
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
    
    public void support(Collection<Object> mappedStatements) {
        for (Object mappedStatement : mappedStatements) {
            if (!(mappedStatement instanceof MappedStatement)) {
                continue;
            }
            MappedStatement statement = (MappedStatement) mappedStatement;
            
            // 缓存
            MappedStatementHelper.addMappedStatement(statement);
        }
        // ..
        preMappedStatement();
    }
    
    private void preMappedStatement() {
        Collection<MappedStatement> mappedStatements = MappedStatementHelper.getMappedStatement();
        for (MappedStatement mappedStatement : mappedStatements) {
            String mappedStatementId = mappedStatement.getId();
            if (!MappedStatementHelper.isLoadedMappedStatement(mappedStatementId)
                    && (mappedStatement.getSqlSource() instanceof ProviderSqlSource)) {
                BaseProvider provider = ProviderHelper.getMethodProvider(mappedStatementId);
                
                // 调用对应的 Provider 处理器，生成 MappedStatement 实例
                provider.invokeProviderMethod(mappedStatement);
                
                // 标记为已加载
                MappedStatementHelper.setLoadedMappedStatement(mappedStatementId);
            }
        }
    }
}
