package in.hocg.squirrel.cashew.sample.module.mapper;


import in.hocg.squirrel.cashew.sample.module.domain.Example;
import in.hocg.squirrel.page.Page;
import in.hocg.squirrel.page.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleMapperTest {
    @Autowired
    ExampleMapper mapper;
    
    @Test
    public void selectById() {
        Optional<Example> example = mapper.selectById(1L);
        log.debug("执行结果: {}", example);
    }
    
    @Test
    public void selectAll() {
        Collection<Example> result = mapper.selectAll();
        log.debug("执行结果: {}", result);
    }
    
    @Test
    public void pageUsePageable() {
        Page<Example> result = mapper.page(Pageable.of(1, 10));
        log.debug("执行结果1: {}", result);
    }
    
    @Test
    public void pageUseRowBounds() {
        List<Example> arg = mapper.page3(new RowBounds(0, 2));
        log.debug("执行结果3: {}", arg);
    }
}