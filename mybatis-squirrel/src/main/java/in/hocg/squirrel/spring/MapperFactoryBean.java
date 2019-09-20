package in.hocg.squirrel.spring;

import in.hocg.squirrel.MappedStatementSupport;
import in.hocg.squirrel.intercepts.pageable.PageableInterceptor;
import in.hocg.squirrel.intercepts.typehandle.TypeHandleInterceptor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * Created by hocgin on 2019/7/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@RequiredArgsConstructor
public class MapperFactoryBean<T> extends SqlSessionDaoSupport
        implements FactoryBean<T> {
    
    /**
     * Mapper 扩展
     */
    private MappedStatementSupport mappedStatementSupport;
    
    /**
     * 是否添加到全局 Configuration
     */
    private boolean addToConfig = true;
    
    /**
     * Mapper 类型
     */
    private final Class<T> mapperInterface;
    
    @Override
    public T getObject() {
        return this.getSqlSession().getMapper(this.mapperInterface);
    }
    
    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }
    
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        Assert.notNull(this.mapperInterface, "字段 mapperInterface 是必须的");
        Configuration configuration = this.getSqlSession().getConfiguration();
        
        // 如果要求加入配置中
        if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
            try {
                // 把 Mapper 加入全局配置中
                configuration.addMapper(this.mapperInterface);
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
                ErrorContext.instance().reset();
            }
        }
    
        // 如果全局配置中已有 Mapper 接口类
        if (configuration.hasMapper(this.mapperInterface)) {
            mappedStatementSupport.support(new ArrayList<>(configuration.getMappedStatements()));
        }
    
        configuration.addInterceptor(new PageableInterceptor());
        configuration.addInterceptor(new TypeHandleInterceptor());
    }
}
