package in.hocg.squirrel.sample.module.mapper;

import in.hocg.squirrel.mapper.CrudMapper;
import in.hocg.squirrel.page.Page;
import in.hocg.squirrel.page.Pageable;
import in.hocg.squirrel.sample.module.domain.Example;
import in.hocg.squirrel.sample.module.vo.CustomTypeHandlerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper
public interface ExampleMapper
        extends
        CrudMapper<Example> {
    
    /**
     * 查找一条数据
     * - 测试 MyBatis 新特性，Optional 返回值
     *
     * @return
     */
    Pageable<Example> page(@Param("pageable") Page pageable);
    
    List<Example> page2(Integer intt);
    
    List<Example> page3(RowBounds rowBounds);
    
    CustomTypeHandlerVo findBy();
}
