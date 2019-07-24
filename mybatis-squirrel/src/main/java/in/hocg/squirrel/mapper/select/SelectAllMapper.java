package in.hocg.squirrel.mapper.select;

import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.provider.methods.SelectAllProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface SelectAllMapper<T> {
    
    /**
     * 查询所有
     *
     * @return
     */
    @SelectProvider(type = SelectAllProvider.class, method = AbstractProvider.PROVIDER_PROXY_METHOD)
    Collection<T> selectAll();
}
