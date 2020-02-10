
## Hystrix


参考: 

https://www.jianshu.com/p/b9af028efebb


https://www.cnblogs.com/gaoyanqing/p/7470085.html


Hystrix 是 Netflix 开源的一款容错系统，能帮助使用者码出具备强大的容错能力和鲁棒性的程序。如果某程序或 clas s要使用 Hystrix，只需简单继承 `HystrixCommand`/`HystrixObservableCommand` 并重写 `run()` / `construct()`，然后调用程序实例化此 class 并执行 `execute()` / `queue()`/ `observe()` / `toObservable()` 。







作者：star24
链接：https://www.jianshu.com/p/b9af028efebb
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。