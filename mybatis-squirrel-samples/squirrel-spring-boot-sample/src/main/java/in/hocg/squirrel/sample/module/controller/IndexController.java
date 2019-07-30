package in.hocg.squirrel.sample.module.controller;

import in.hocg.squirrel.sample.module.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor
public class IndexController {
    
    private final ExampleService service;
    
    @GetMapping("page")
    public Object page() {
        return service.page();
    }
}
