package in.hocg.squirrel.mapper.insert;

import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.provider.BaseProvider;
import in.hocg.squirrel.provider.insert.InsertOneProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface InsertOneMapper<T, Id extends Serializable> {
    
    /**
     * 新增
     *
     * @param bean
     * @return 影响的行数
     */
    @InsertProvider(type = InsertOneProvider.class, method = BaseProvider.PROVIDER_METHOD)
    Integer insertOne(@Param(Constants.BEAN_PARAMETER) T bean);
}
