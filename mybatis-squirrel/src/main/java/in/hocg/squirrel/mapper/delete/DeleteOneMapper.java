package in.hocg.squirrel.mapper.delete;

import in.hocg.squirrel.core.Constants;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.methods.DeleteOneProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface DeleteOneMapper<T, Id extends Serializable> {
    
    /**
     * 删除
     *
     * @param id 主键
     * @return 影响的行数
     */
    @DeleteProvider(type = DeleteOneProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    int deleteOne(@Param(Constants.KEY_PARAMETER) Id id);
    
}
