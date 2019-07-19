package in.hocg.squirrel.mapper;

import java.io.Serializable;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CountMapper<T, Id extends Serializable> {
    
    Long count();
}
