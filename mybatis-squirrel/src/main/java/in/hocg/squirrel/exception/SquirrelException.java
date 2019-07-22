package in.hocg.squirrel.exception;

import in.hocg.squirrel.utils.TextFormatter;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class SquirrelException extends RuntimeException {
    
    private SquirrelException() {}
    
    private SquirrelException(String message) {
        super(message);
    }
    
    public static SquirrelException wrap(String message) {
        return new SquirrelException(message);
    }
    
    public static SquirrelException wrap(String message, Object... args) {
        return new SquirrelException(TextFormatter.format(message, args));
    }
}
