package in.hocg.squirrel.mapper;

import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.methods.CountAllProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CountAllMapper<T> {
    
    /**
     * 计数
     *
     * @return Long! >=0
     */
    @SelectProvider(type = CountAllProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    long countAll();
}
