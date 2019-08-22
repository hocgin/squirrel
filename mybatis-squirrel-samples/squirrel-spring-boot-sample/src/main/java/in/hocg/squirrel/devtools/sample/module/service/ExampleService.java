package in.hocg.squirrel.devtools.sample.module.service;

import in.hocg.squirrel.page.Pageable;
import in.hocg.squirrel.devtools.sample.module.domain.Example;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ExampleService {
    
    Pageable<Example> page();
}
