package in.hocg.squirrel.devtools;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@ConfigurationProperties(prefix = DevtoolsProperties.PREFIX)
public class DevtoolsProperties {
    public static final String PREFIX = "mybatis.devtools";
    
    /**
     * 自动加载 Mapper
     */
    private boolean reloadMapper = true;
    
    /**
     * 自动拼接完整 SQL
     */
    private boolean showSql = true;
    
}
