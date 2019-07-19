package in.hocg.squirrel.page;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class DefaultPage<T> implements Page<T> {
    /**
     * 总数
     */
    private long total = 0;
    
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;
    
    /**
     * 当前页
     */
    private long current = 1;
    
    /**
     * 分页数据
     */
    private List<T> records = Collections.emptyList();
    
    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }
    
    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }
    
    
    @Override
    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
    
    @Override
    public long getCurrent() {
        return this.current;
    }
    
    @Override
    public long getSize() {
        return this.size;
    }
    
    @Override
    public Page<T> setSize(long size) {
        this.size = size;
        return this;
    }
    
    @Override
    public long getTotal() {
        return this.total;
    }
    
    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }
    
    @Override
    public List<T> getRecords() {
        return this.records;
    }
    
    @Override
    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }
}
