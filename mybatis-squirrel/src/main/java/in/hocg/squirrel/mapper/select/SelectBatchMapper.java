package in.hocg.squirrel.mapper.select;

import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.methods.SelectBatchProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hocgin on 2019-07-23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface SelectBatchMapper<T, Id extends Serializable> {
    
    /**
     * 使用主键查询多个
     *
     * @param id
     * @return
     */
    @SelectProvider(type = SelectBatchProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    List<T> selectBatch(@Param(Constants.ARRAY_PARAMETER) Id... id);
}
