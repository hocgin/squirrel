package in.hocg.squirrel.core;

import lombok.Getter;

/**
 * Created by hocgin on 2019-07-31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
public enum Dialect {
    // MySql
    MySQL(":mysql:"),
    // Oracle
    Oracle(":jdbc:"),
    // MariaDB
    MariaDB(":mariadb:"),
    // 其他
    Unknown("?");
    
    private String flag;
    
    Dialect(String flag) {
        this.flag = flag;
    }
    
    /**
     * 根据标识获取方言
     *
     * @param flag
     * @return
     */
    public static Dialect of(String flag) {
        for (Dialect dialect : Dialect.values()) {
            if (dialect.flag.contains(flag)) {
                return dialect;
            }
        }
        return Dialect.Unknown;
    }
}
