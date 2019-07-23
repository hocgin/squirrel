package in.hocg.squirrel.sample.module.mapper;

import in.hocg.squirrel.mapper.CountAllMapper;
import in.hocg.squirrel.mapper.delete.DeleteOneMapper;
import in.hocg.squirrel.mapper.insert.InsertOneMapper;
import in.hocg.squirrel.mapper.select.SelectAllMapper;
import in.hocg.squirrel.mapper.select.SelectBatchMapper;
import in.hocg.squirrel.mapper.select.SelectOneMapper;
import in.hocg.squirrel.mapper.update.UpdateOneMapper;
import in.hocg.squirrel.sample.module.domain.Example;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper
public interface ExampleMapper
        extends
        CountAllMapper<Example, Long>,
        SelectOneMapper<Example, Long>,
        DeleteOneMapper<Example, Long>,
        UpdateOneMapper<Example, Long>,
        SelectBatchMapper<Example, Long>,
        SelectAllMapper<Example, Long>,
        InsertOneMapper<Example, Long> {
    
    
    /**
     * 查找一条数据
     * - 测试 MyBatis 新特性，Optional 返回值
     *
     * @return
     */
    Optional<Example> findFirst();
    
    
}
