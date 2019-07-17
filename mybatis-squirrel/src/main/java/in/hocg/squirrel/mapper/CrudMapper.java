package in.hocg.squirrel.mapper;

import in.hocg.squirrel.provider.BaseProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CrudMapper<T, Id extends Serializable> {
    
    @SelectProvider(type = BaseProvider.class, method = "method")
    Optional<T> findOne();
}
