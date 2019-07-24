package in.hocg.squirrel.mapper.select;

import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.provider.BaseProvider;
import in.hocg.squirrel.provider.select.SelectOneProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface SelectOneMapper<T, Id extends Serializable> {
    
    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    @SelectProvider(type = SelectOneProvider.class, method = BaseProvider.PROVIDER_PROXY_METHOD)
    Optional<T> selectOne(@Param(Constants.KEY_PARAMETER) Id id);
}
