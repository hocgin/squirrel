package in.hocg.squirrel.mapper;

import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.SelectOneProvider;
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
public interface SelectOneMapper<T> {
    
    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    @SelectProvider(type = SelectOneProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    Optional<T> selectOne(@Param(Constants.KEY_PARAMETER) Serializable id);
}