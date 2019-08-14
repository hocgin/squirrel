package in.hocg.squirrel.mapper;

import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.DeleteByIdsProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface DeleteByIdsMapper<T> {
    
    /**
     * 删除
     *
     * @param id 主键
     * @return 影响的行数
     */
    @DeleteProvider(type = DeleteByIdsProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    int deleteByIds(@Param(Constants.ARRAY_PARAMETER) Serializable... id);
    
}
