package in.hocg.squirrel.mapper;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CrudMapper<T>
        extends
        DeleteByIdMapper<T>,
        DeleteByIdsMapper<T>,
        InsertMapper<T>,
        UpdateByIdMapper<T>,
        UpdateIgnoreNullByIdMapper<T>,
        CountAllMapper<T>,
        SelectAllMapper<T>,
        SelectByIdMapper<T>,
        SelectByIdsMapper<T> {
}
