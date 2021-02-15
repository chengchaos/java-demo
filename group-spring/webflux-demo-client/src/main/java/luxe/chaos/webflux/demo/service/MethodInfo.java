package luxe.chaos.webflux.demo.service;

import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * <p>
 * <strong>
 *     方法调用信息
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 11:40 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class MethodInfo {

    private String url;

    private HttpMethod httpMethod;

    private Map<String, Object> args;

    private Mono<?> body;

    private Class<?> bodyElementType;

    /**
     * Flux 还是 Mono
     */
    private boolean isFlux;

    private Class<?> returnElementType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public Mono<?> getBody() {
        return body;
    }

    public void setBody(Mono<?> body) {
        this.body = body;
    }

    public Class<?> getBodyElementType() {
        return bodyElementType;
    }

    public void setBodyElementType(Class<?> bodyElementType) {
        this.bodyElementType = bodyElementType;
    }

    public boolean isFlux() {
        return isFlux;
    }

    public void setFlux(boolean flux) {
        isFlux = flux;
    }

    public Class<?> getReturnElementType() {
        return returnElementType;
    }

    public void setReturnElementType(Class<?> returnElementType) {
        this.returnElementType = returnElementType;
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "url='" + url + '\'' +
                ", httpMethod=" + httpMethod +
                ", args=" + args +
                ", body=" + body +
                '}';
    }
}
