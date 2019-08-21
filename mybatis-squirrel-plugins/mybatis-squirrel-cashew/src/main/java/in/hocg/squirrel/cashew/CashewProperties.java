package in.hocg.squirrel.cashew;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hocgin on 2019-08-21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@ConfigurationProperties(prefix = CashewProperties.PREFIX)
public class CashewProperties {
    public static final String PREFIX = "mybatis.cashew";
}
