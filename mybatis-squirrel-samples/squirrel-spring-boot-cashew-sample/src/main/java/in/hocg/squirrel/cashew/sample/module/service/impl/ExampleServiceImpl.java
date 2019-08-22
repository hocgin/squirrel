package in.hocg.squirrel.cashew.sample.module.service.impl;

import in.hocg.squirrel.cashew.sample.module.domain.Example;
import in.hocg.squirrel.cashew.sample.module.mapper.ExampleMapper;
import in.hocg.squirrel.cashew.sample.module.service.ExampleService;
import in.hocg.squirrel.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {
    
    private final ExampleMapper exampleMapper;
    
    @Override
    public Pageable<Example> page() {
        return exampleMapper.page(Pageable.of(1, 10));
    }
}
