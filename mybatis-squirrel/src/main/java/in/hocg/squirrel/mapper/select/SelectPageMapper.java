package in.hocg.squirrel.mapper.select;

import in.hocg.squirrel.page.Page;

import java.io.Serializable;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface SelectPageMapper<T, Id extends Serializable> {
    
    Page<T> selectPage();
    
}
