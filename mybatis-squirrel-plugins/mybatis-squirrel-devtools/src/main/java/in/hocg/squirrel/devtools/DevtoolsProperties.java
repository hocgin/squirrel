package in.hocg.squirrel.devtools;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@ConfigurationProperties(prefix = DevtoolsProperties.PREFIX)
public class DevtoolsProperties {
    public static final String PREFIX = "mybatis.devtools";
    
    
}
