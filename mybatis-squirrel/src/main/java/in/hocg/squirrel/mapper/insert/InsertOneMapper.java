package in.hocg.squirrel.mapper.insert;

import in.hocg.squirrel.provider.insert.InsertOneProvider;
import org.apache.ibatis.annotations.InsertProvider;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface InsertOneMapper<T, Id extends Serializable> {
    
    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @InsertProvider(type = InsertOneProvider.class, method = "method")
    Integer insertOne(T entity);
}
