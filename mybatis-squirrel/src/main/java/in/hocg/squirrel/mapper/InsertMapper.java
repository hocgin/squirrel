package in.hocg.squirrel.mapper;

import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.InsertProvider;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface InsertMapper<T> {
    
    /**
     * 新增
     *
     * @param bean
     * @return 影响的行数
     */
    @org.apache.ibatis.annotations.InsertProvider(type = InsertProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    int insert(@Param(Constants.BEAN_PARAMETER) T bean);
}
