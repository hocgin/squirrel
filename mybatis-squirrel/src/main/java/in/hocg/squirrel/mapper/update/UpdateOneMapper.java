package in.hocg.squirrel.mapper.update;

import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.provider.BaseProvider;
import in.hocg.squirrel.provider.update.UpdateOneProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface UpdateOneMapper<T, Id extends Serializable> {
    
    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @UpdateProvider(type = UpdateOneProvider.class, method = BaseProvider.PROVIDER_METHOD)
    Integer updateOne(@Param(Constants.BEAN_PARAMETER) T entity);
}
