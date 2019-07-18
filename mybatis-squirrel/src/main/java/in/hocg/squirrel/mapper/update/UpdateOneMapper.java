package in.hocg.squirrel.mapper.update;

import in.hocg.squirrel.provider.update.UpdateOneProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface UpdateOneMapper<T, Id extends Serializable> {
    
    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @SelectProvider(type = UpdateOneProvider.class, method = "method")
    Integer updateOne(T entity);
}
