package in.hocg.squirrel.mapper;

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
