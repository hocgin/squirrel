package in.hocg.squirrel.cashew.sample.module.service;

import in.hocg.squirrel.cashew.sample.module.domain.Example;
import in.hocg.squirrel.page.Pageable;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ExampleService {
    
    Pageable<Example> page();
}
