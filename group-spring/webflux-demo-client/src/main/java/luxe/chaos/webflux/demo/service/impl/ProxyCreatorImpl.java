package luxe.chaos.webflux.demo.service.impl;

import luxe.chaos.webflux.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 使用 JDK 动态代理实现
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 11:33 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Component
public class ProxyCreatorImpl implements ProxyCreator {

    private static final Logger logger = LoggerFactory.getLogger(ProxyCreatorImpl.class);

    private final RestHandler restHandler;

    public ProxyCreatorImpl(RestHandler restHandler) {
        this.restHandler = restHandler;
    }

    @Override
    public Object createProxy(Class<?> type) {

        logger.debug("创建 Proxy 方法");
        // 根据接口得到 API 服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        logger.debug("serverInfo => {}", serverInfo);

        restHandler.init(serverInfo);

        ClassLoader loader = this.getClass().getClassLoader();

        return Proxy.newProxyInstance(loader, new Class<?>[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 根据方法和参数得到调用信息

                MethodInfo methodInfo = extractMethodInfo(method, args);

                logger.debug("MethodInfo => {}", methodInfo);
                Object result = restHandler.invokeRest(serverInfo, methodInfo);

                logger.debug("Get result is => {}", result);
                return result;
            }
        });
    }

    private ServerInfo extractServerInfo(Class<?> type) {

        ServerInfo serverInfo = new ServerInfo();
        ApiServer apiServer = type.getAnnotation(ApiServer.class);
        String url = apiServer.value();
        logger.debug("Api server value => url => {}", url);
        serverInfo.setUrl(url);

        return serverInfo;
    }

    private MethodInfo extractMethodInfo(Method method, Object[] args) {

        logger.debug("抽取方法信息 *** ***");
        MethodInfo methodInfo = new MethodInfo();

        Annotation[] annotations = method.getAnnotations();
        
        for (Annotation ann : annotations) {
            if (ann instanceof GetMapping) {
                GetMapping a = (GetMapping) ann;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.GET);
            } else if (ann instanceof PostMapping) {
                PostMapping a = (PostMapping) ann;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.POST);
            } else if (ann instanceof DeleteMapping) {
                DeleteMapping a = (DeleteMapping) ann;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.DELETE);
            } else if (ann instanceof PutMapping) {
                PutMapping a = (PutMapping) ann;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.PUT);
            } else {
                throw new RuntimeException("未知方法");
            }
        }

        Parameter[] parameters = method.getParameters();
        Map<String, Object> params = new LinkedHashMap<>();

        for (int i = 0; i < parameters.length; i++) {
            // 是否带 @PathVariable 注解
            PathVariable annoPath = parameters[i].getAnnotation(PathVariable.class);
            if (annoPath != null) {
                params.put(annoPath.value(), args[i]);
                continue;
            }
            RequestBody annoReqBody = parameters[i].getAnnotation(RequestBody.class);
            if (annoReqBody != null) {
                methodInfo.setBody((Mono<?>) args[i]);
                methodInfo.setBodyElementType(this.extractElementType(parameters[i].getParameterizedType()));
            }
        }

        methodInfo.setArgs(params);

        Class<?> returnType = method.getReturnType();

        if (returnType.isAssignableFrom(Flux.class)) {
            methodInfo.setFlux(true);
        }
        Class<?> elementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(elementType);

        return methodInfo;
    }

    /**
     * 得到实际的泛型信息
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {

        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType)
                .getActualTypeArguments();

        return (Class<?>) actualTypeArguments[0];

    }
}
