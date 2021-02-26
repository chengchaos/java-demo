
## interrupt()

`interrupt() `方法仅仅是通知线程，线程有机会执行一些后续操作，同时也可以无视这个通知。
被 interrupt 的线程，有两种方式接收通知：一种是异常， 另一种是主动检测。

### 通过异常接收通知

当线程 A 处于 WAITING、 TIMED_WAITING 状态时， 如果其他线程调用线程A的 `interrupt()`方法，则会使线程 A 返回到 RUNNABLE 状态，同时线程 A 的代码会触发 `InterruptedException` 异常。线程转换到 WAITING、TIMED_WAITING 状态的触发条件，都是调用了类似 `wait()`、`join()`、`sleep()` 这样的方法， 我们看这些方法的签名时，发现都会 `throws InterruptedException` 这个异常。这个异常的触发条件就是：其他线程调用了该线程的 `interrupt()` 方法。

当线程 A 处于 RUNNABLE 状态时，并且阻塞在 `java.nio.channels.InterruptibleChannel` 上时， 如果其他线程调用线程A的 `interrupt()` 方法，线程 A 会触发 `java.nio.channels.ClosedByInterruptException` 这个异常；
当阻塞在 `java.nio.channels.Selector` 上 时，如果其他线程调用线程 A 的 `interrupt()` 方法，线程 A 的 `java.nio.channels.Selector` 会立即返回。

## 主动检测通知

如果线程处于 RUNNABLE 状态，并且没有阻塞在某个 I/O 操作上，例如中断计算基因组序列的线程 A，此时就得依赖线程 A 主动检测中断状态了。如果其他线程调用线程 A 的 `interrupt()` 方法， 那么线程A可以通过` isInterrupted()` 方法， 来检测自己是不是被中断了。

## stop()方法

`stop()` 方法会真的杀死线程。如果线程持有 `ReentrantLock` 锁，被 `stop()` 的线程并不会自动调用 `ReentrantLock` 的 `unlock()`去释放锁，那其他线程就再也没机会获得 `ReentrantLock` 锁， 这样其他线程就再也不能执行 `ReentrantLock` 锁锁住的代码逻辑。 所以该方法就不建议使用了， 类似的方法还有 `suspend()` 和 `resume()` 方法， 这两个方法同样也都不建议使用了， 所以这里也就不多介绍了。