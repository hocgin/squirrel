package in.hocg.squirrel.mapper;

import in.hocg.squirrel.mapper.delete.DeleteOneMapper;
import in.hocg.squirrel.mapper.insert.InsertOneMapper;
import in.hocg.squirrel.mapper.select.SelectAllMapper;
import in.hocg.squirrel.mapper.update.UpdateOneMapper;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CrudMapper<T>
        extends
        DeleteOneMapper<T>,
        InsertOneMapper<T>,
        UpdateOneMapper<T>,
        SelectAllMapper<T> {
}
