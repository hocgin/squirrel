package in.hocg.squirrel.page;

/**
 * Created by hocgin on 2019-07-25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Pageable {
    
    /**
     * 页码
     *
     * @return
     */
    int getPage();
    
    /**
     * 数量
     *
     * @return
     */
    int getSize();
}
