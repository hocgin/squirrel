package in.hocg.squirrel.page;

import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@ToString
public class Pageable<T> implements Page<T> {
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
    private int page = 1;

    /**
     * 是否统计总数
     */
    private boolean isSearchCount = true;

    /**
     * 分页数据
     */
    private List<T> records = Collections.emptyList();

    /**
     * 静态构造函数
     *
     * @param page
     * @param size
     * @param <T>
     * @return r
     */
    public static <T> Page<T> of(int page, int size) {
        return new Pageable<T>()
                .setPage(page)
                .setSize(size);
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.page > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.page < this.getTotalPage();
    }


    @Override
    public Page<T> setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getPage() {
        return this.page;
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

    @Override
    public boolean isSearchCount() {
        return this.isSearchCount;
    }

    public Page<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }
}
