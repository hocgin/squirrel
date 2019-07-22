package in.hocg.squirrel.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class LangKit {
    
    /**
     * 判断集合是否为 NULL 或者 空
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection c) {
        if (c == null || c.isEmpty()) {
            return true;
        }
        return false;
    }

}
