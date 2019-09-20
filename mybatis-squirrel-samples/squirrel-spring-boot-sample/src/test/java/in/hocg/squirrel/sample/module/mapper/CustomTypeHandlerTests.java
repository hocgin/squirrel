package in.hocg.squirrel.sample.module.mapper;

import in.hocg.squirrel.page.Page;
import in.hocg.squirrel.page.Pageable;
import in.hocg.squirrel.sample.module.vo.CustomTypeHandlerVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hocgin on 2019-09-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomTypeHandlerTests {
    @Autowired
    ExampleMapper mapper;
    
    
    @Test
    public void test3() {
        CustomTypeHandlerVo vo = mapper.findBy();
        log.debug("执行结果: {}", vo);
    }
    
    
    @Test
    public void page() {
        Page<CustomTypeHandlerVo> vo = mapper.pageUseCustomTypeHandle(Pageable.of(1, 10));
        log.debug("执行结果: {}", vo);
    }
}
