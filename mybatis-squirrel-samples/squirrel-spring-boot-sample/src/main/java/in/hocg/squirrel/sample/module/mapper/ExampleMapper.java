package in.hocg.squirrel.sample.module.mapper;

import in.hocg.squirrel.mapper.CountAllMapper;
import in.hocg.squirrel.mapper.DeleteOneMapper;
import in.hocg.squirrel.mapper.InsertOneMapper;
import in.hocg.squirrel.mapper.SelectAllMapper;
import in.hocg.squirrel.mapper.SelectBatchMapper;
import in.hocg.squirrel.mapper.SelectOneMapper;
import in.hocg.squirrel.mapper.UpdateOneMapper;
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
        CountAllMapper<Example>,
        SelectOneMapper<Example>,
        DeleteOneMapper<Example>,
        UpdateOneMapper<Example>,
        SelectBatchMapper<Example>,
        SelectAllMapper<Example>,
        InsertOneMapper<Example> {
    
    /**
     * 查找一条数据
     * - 测试 MyBatis 新特性，Optional 返回值
     *
     * @return
     */
    Optional<Example> findFirst();
    
}
