package in.hocg.squirrel.sample.module.vo;

import in.hocg.squirrel.intercepts.typehandle.CustomTypeHandle;
import in.hocg.squirrel.sample.typehandler.CustomTypeHandler;
import lombok.Data;

/**
 * Created by hocgin on 2019-09-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CustomTypeHandlerVo {
    
    private Long id;
    
    @CustomTypeHandle(typeHandler = CustomTypeHandler.class)
    private String name;
}
