package luxe.chaos.webflux.demo.service;

/**
 * <p>
 * <strong>
 *     REST 请求的接口
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 11:45 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public interface RestHandler {

    void init(ServerInfo serverInfo);

    Object invokeRest(ServerInfo serverInfo, MethodInfo methodInfo);
}
