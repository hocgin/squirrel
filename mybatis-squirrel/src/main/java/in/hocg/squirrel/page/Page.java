package in.hocg.squirrel.page;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 分页接口
 * <p>
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Page<T> extends Serializable {
    
    /**
     * 设置当前页
     *
     * @param current
     * @return
     */
    Page<T> setCurrent(long current);
    
    /**
     * 获取当前页
     *
     * @return
     */
    long getCurrent();
    
    /**
     * 分页显示数量
     *
     * @return
     */
    long getSize();
    
    /**
     * 设置分页显示数量
     *
     * @param size
     * @return
     */
    Page<T> setSize(long size);
    
    /**
     * 查询数据总数
     *
     * @return
     */
    long getTotal();
    
    /**
     * 设置数据总数
     *
     * @param total
     * @return
     */
    Page<T> setTotal(long total);
    
    /**
     * 当前分页总页数
     *
     * @return
     */
    default long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }
    
    /**
     * 数据
     *
     * @return
     */
    List<T> getRecords();
    
    /**
     * 数据
     *
     * @param records
     * @return
     */
    Page<T> setRecords(List<T> records);
    
    /**
     * 计算在表上的偏移量
     *
     * @return
     */
    default long offset() {
        return getCurrent() > 0 ? (getCurrent() - 1) * getSize() : 0;
    }
    
    /**
     * 类型转换和处理
     *
     * @param mapper
     * @param <R>
     * @return
     */
    default <R> Page<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        return ((Page<R>) this).setRecords(collect);
    }
}
