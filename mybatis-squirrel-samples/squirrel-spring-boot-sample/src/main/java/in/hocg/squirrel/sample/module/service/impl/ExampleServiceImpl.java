package in.hocg.squirrel.sample.module.service.impl;

import in.hocg.squirrel.page.Pageable;
import in.hocg.squirrel.sample.module.domain.Example;
import in.hocg.squirrel.sample.module.mapper.ExampleMapper;
import in.hocg.squirrel.sample.module.service.ExampleService;
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
