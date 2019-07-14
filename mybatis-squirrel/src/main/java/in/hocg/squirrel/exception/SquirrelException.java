package in.hocg.squirrel.exception;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class SquirrelException extends RuntimeException {
    
    public SquirrelException() {
    }
    
    public SquirrelException(String message) {
        super(message);
    }
}
