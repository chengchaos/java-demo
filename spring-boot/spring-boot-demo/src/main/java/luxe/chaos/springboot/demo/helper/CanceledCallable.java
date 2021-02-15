package luxe.chaos.springboot.demo.helper;

import javax.servlet.AsyncContext;
import java.util.concurrent.Callable;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/7/2021 2:10 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class CanceledCallable implements Callable<Object> {

    public final AsyncContext asyncContext;

    public CanceledCallable(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }


    @Override
    public Object call() throws Exception {
        return null;
    }
}
