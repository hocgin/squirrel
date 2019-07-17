package in.hocg.squirrel.mapper.select;

import in.hocg.squirrel.provider.select.SelectOneProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

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
    @SelectProvider(type = SelectOneProvider.class, method = "method")
    T selectOne(Id id);
}
