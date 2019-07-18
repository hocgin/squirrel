package in.hocg.squirrel.mapper;

import in.hocg.squirrel.mapper.delete.DeleteOneMapper;
import in.hocg.squirrel.mapper.insert.InsertOneMapper;
import in.hocg.squirrel.mapper.select.SelectAllMapper;
import in.hocg.squirrel.mapper.update.UpdateOneMapper;

import java.io.Serializable;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CrudMapper<T, Id extends Serializable>
        extends DeleteOneMapper<T, Id>,
        InsertOneMapper<T, Id>,
        UpdateOneMapper<T, Id>,
        SelectAllMapper<T, Id> {
}
