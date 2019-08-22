package in.hocg.squirrel.cashew.sample.module.domain;


import in.hocg.squirrel.annotation.Column;
import in.hocg.squirrel.annotation.Id;
import in.hocg.squirrel.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2019/5/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Table(name = "t_example")
public class Example extends SupperTable {
    
    @Id(keyGenerator = Jdbc3KeyGenerator.class)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "name2")
    private String title;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
