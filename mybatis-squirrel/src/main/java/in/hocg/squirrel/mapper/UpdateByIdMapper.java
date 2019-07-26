package in.hocg.squirrel.mapper;

import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.UpdateByIdProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface UpdateByIdMapper<T> {
    
    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @UpdateProvider(type = UpdateByIdProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    int updateById(@Param(Constants.BEAN_PARAMETER) T entity);
}
