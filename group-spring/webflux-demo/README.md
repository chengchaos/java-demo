

## 异步 Servlet

基本写法

```java
import java.io.IOException;

public class SomeServlet {
    public void doGetLite(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // 首先开启异步上下文
        AsyncContext asyncContext = request.startAsync();
        HttpServletRequest req = asyncContext.getRequest();
        // 另外一个线程执行。
        CompletableFuture.runAsync(() -> {
            doSomeing(req);
            // 通知异步上下文任务完成。
            asyncContext.complete();
        });
    }
}
```

## SSE(Server-Sent Events)

```java
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SomeServlet {
    public void doGetLite(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        for (int i = 0; i < 5; i++) {
            // 也可以自定义一个事件标记
            response.getWriter().write("event:me\n\n");
            // 结尾加两个换行
            response.getWriter().write("data:" + i + "\n\n");
            response.getWriter().flush();


            TimeUnit.SECONDS.sleep(1L);
        }
    }
}
```


```html
<script type="text/javascript">
    // 默认自动重连
    var sse = new EventSource("target-url");
    sse.onmessage = function (ev) {
        console.log("message", ev.data, ev);
    }
    
    sse.addEventListener("me", function (ev) {
        console.log("me event", ev);
        if (e.data === 3) {
            // 手动关闭自动重连。
            sse.close();
        }
    })
</script>
```


```shell
bin/mongod --dbpath d:\dbpath --smallfiles
```


## HandlerFunction 开发

1， 输入ServerRequest，返回 ServerResponse
2, 开发 RouterFunction：将 url 和 HandlerFunction 做对应
3，开发 HttpHandler
4，Server 处理