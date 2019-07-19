package in.hocg.squirrel.mapper;

import in.hocg.squirrel.mapper.select.SelectPageMapper;

import java.io.Serializable;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PageMapper<T, Id extends Serializable>
        extends SelectPageMapper<T, Id> {
}
