package in.hocg.squirrel.sample.module.mapper;


import in.hocg.squirrel.page.Pageable;
import in.hocg.squirrel.sample.module.domain.Example;
import lombok.extern.slf4j.Slf4j;
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
    public void selectOne() {
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
    public void deleteOne() {
        int result = mapper.deleteById(1L);
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
    public void selectBatch() {
        List<Example> result = mapper.selectBatchIds(1L, 2L, 3L);
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
    public void test() {
        mapper.page(new Pageable() {
            @Override
            public int getPage() {
                return 0;
            }
        
            @Override
            public int getSize() {
                return 0;
            }
        });
    }
}