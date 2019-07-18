package in.hocg.squirrel.sample.module.domain;


import in.hocg.squirrel.core.annotation.Column;
import in.hocg.squirrel.core.annotation.Id;
import in.hocg.squirrel.core.annotation.Table;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@ToString
@Data
@Table(name = "t_example")
public class Example extends SupperTable {
    
    @Id
    private Long id;
    
    @Column
    private String name;
    
    @Column
    private LocalDateTime createdAt;
}
