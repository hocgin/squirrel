package in.hocg.squirrel.sample.module.vo;

import in.hocg.squirrel.annotation.Column;
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
    
    @Column(name = "name", typeHandler = CustomTypeHandler.class)
    private String name;
}
