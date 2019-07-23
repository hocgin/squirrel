package in.hocg.squirrel.mapper;

import in.hocg.squirrel.provider.BaseProvider;
import in.hocg.squirrel.provider.CountAllProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CountAllMapper<T, Id extends Serializable> {
    
    /**
     * 计数
     *
     * @return Long! >=0
     */
    @SelectProvider(type = CountAllProvider.class, method = BaseProvider.PROVIDER_METHOD)
    Long countAll();
}
