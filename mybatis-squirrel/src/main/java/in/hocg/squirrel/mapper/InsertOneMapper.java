package in.hocg.squirrel.mapper;

import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.InsertOneProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface InsertOneMapper<T> {
    
    /**
     * 新增
     *
     * @param bean
     * @return 影响的行数
     */
    @InsertProvider(type = InsertOneProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    int insertOne(@Param(Constants.BEAN_PARAMETER) T bean);
}
