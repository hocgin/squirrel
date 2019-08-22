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

import java.time.LocalDateTime;
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
    public void insertOne() {
        Example entity = new Example();
        entity.setCreatedAt(LocalDateTime.now());
        entity.setName("?");
        int result = mapper.insert(entity);
        log.debug("执行结果: {} entity:{}", result, entity);
    }
    
    @Test
    public void deleteById() {
        int result = mapper.deleteById(1L);
        log.debug("执行结果: {}", result);
    }
    
    @Test
    public void deleteByIds() {
        int result = mapper.deleteByIds(1L, 2L, 3L);
        log.debug("执行结果: {}", result);
    }

    @Test
    public void selectAll() {
        Collection<Example> result = mapper.selectAll();
        log.debug("执行结果: {}", result);
    }

    @Test
    public void countAll() {
        long result = mapper.countAll();
        log.debug("执行结果: {}", result);
    }

    @Test
    public void selectByIds() {
        List<Example> result = mapper.selectByIds(1L, 2L, 3L);
        log.debug("执行结果: {}", result);
    }
    
    @Test
    public void updateOne() {
        Example entity = new Example();
        entity.setId(2L);
        entity.setName("66");
        entity.setCreatedAt(LocalDateTime.now());
        int result = mapper.updateById(entity);
        log.debug("执行结果: {}", result);
    }
    @Test
    public void updateIgnoreNullOne() {
        Example entity = new Example();
        entity.setId(2L);
//        entity.setTitle("66");
        entity.setCreatedAt(LocalDateTime.now());
        int result = mapper.updateIgnoreNullById(entity);
        log.debug("执行结果: {}", result);
    }
    
    @Test
    public void test() {
        Page<Example> result = mapper.page(Pageable.of(1, 10));
        log.debug("执行结果1: {}", result);
        log.debug("执行结果2: {}", mapper.page2(2));
        System.out.println();
    }
    
    @Test
    public void test3() {
        List<Example> arg = mapper.page3(new RowBounds(0, 2));
        log.debug("执行结果3: {}", arg);
    }
}