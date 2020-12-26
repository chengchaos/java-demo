package luxe.chaos.helper.log


import org.slf4j.{Logger => SLF4JLogger}
import org.slf4j.Marker

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/12/1 20:40 <br />
 * @since [ 产品模块版本 ]
 * @see [ 相关类方法 ]
 *
 */
class Logger(val logger: SLF4JLogger) extends Serializable {

  import scala.language.implicitConversions


  @inline final def name: String = logger.getName


  @inline final def isTraceEnabled: Boolean = logger.isTraceEnabled


  @inline final def trace(format: String, t: => Throwable): Unit =
    if (isTraceEnabled) logger.trace(format, t)


  @inline final def trace(format: String, arguments: Any*): Unit = logger.trace(format, arguments: _*)


  @inline final def trace(msg: => Any): Unit =
    if (isTraceEnabled) logger.trace(msg.toString)


  @inline final def isTraceEnabled(marker: Marker): Boolean =
    logger.isTraceEnabled(marker)


  @inline final def trace(marker: Marker, msg: String): Unit =
    logger.trace(marker, msg)


  @inline final def trace(mkr: Marker, message: String, t: => Throwable): Unit =
    if (isTraceEnabled) logger.trace(mkr, message, t)


  @inline final def trace(marker: Marker, format: String, other: Any*): Unit =
    if (isTraceEnabled) logger.trace(marker, format, other: _*)


  @inline final def trace(marker: Marker, msg: => Any): Unit =
    if (logger.isTraceEnabled(marker))
      logger.trace(marker, msg.toString)


  @inline final def isDebugEnabled: Boolean = logger.isDebugEnabled


  @inline final def debug(msg: String, t: Throwable): Unit =
    logger.debug(msg, t)


  @inline final def debug(format: String, arguments: Any*): Unit =
    logger.debug(format, arguments: _*)


  @inline final def debug(msg: => Any): Unit = logger.debug(msg.toString)


  @inline final def isDebugEnabled(marker: Marker): Boolean =
    logger.isDebugEnabled(marker)


  @inline final def debug(marker: Marker, msg: String, t: Throwable): Unit =
    logger.debug(marker, msg, t)


  @inline final def debug(marker: Marker, format: String, arguments: Any*): Unit =
    logger.debug(marker, format, arguments: _*)


  @inline final def debug(marker: Marker, msg: => Any): Unit =
    if (logger.isDebugEnabled(marker)) logger.debug(marker, msg.toString)


  @inline final def isInfoEnable: Boolean = logger.isInfoEnabled


  @inline final def info(first: => Any, arguments: Any*): Unit =
    if (logger.isInfoEnabled)
      if (arguments == null)
        logger.info(first.toString)
      else
        logger.info(first.toString, arguments: _*)


  @inline final def info(msg: String, t: Throwable): Unit = logger.info(msg, t)


  @inline final def isInfoEnabled(marker: Marker): Boolean = logger.isInfoEnabled(marker)


  @inline final def info(marker: Marker, first: => Any, arguments: Any*): Unit =
    if (logger.isInfoEnabled(marker))
      if (arguments == null)
        logger.info(first.toString)
      else
        logger.info(marker, first.toString, arguments: _*)


  @inline final def info(marker: Marker, msg: String, t: Throwable): Unit =
    logger.info(marker, msg, t)


  private implicit def _any2String(msg: Any): String =
    msg match {
      case null => "<null>"
      case b: Byte => java.lang.Byte.toString(b)
      case c: Char => java.lang.Character.toString(c)
      case s: Short => java.lang.Short.toString(s)
      case i: Int => java.lang.Integer.toString(i)
      case l: Long => java.lang.Long.toString(l)
      case f: Float => java.lang.Float.toString(f)
      case d: Double => java.lang.Double.toString(d)
      case bo: Boolean => java.lang.Boolean.toString(bo)

      case _ => msg.toString
    }
}


trait Logging {
  @transient private lazy val _logger = Logger(getClass)

  protected def logger: Logger = _logger

}

object Logger {

  import scala.reflect.{classTag, ClassTag}

  val RootLoggerName: String = SLF4JLogger.ROOT_LOGGER_NAME

  def apply(name: String): Logger = new Logger(org.slf4j.LoggerFactory.getLogger(name))

  def apply(cls: Class[_]): Logger = apply(cls.getName)

  def apply[C: ClassTag](): Logger = apply(classTag[C].runtimeClass.getName)

  def rootLogger: Logger = apply(RootLoggerName)
}