package luxe.chaos.threads.demo001.dt;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/20/2021 3:35 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DateTimeHelper {

    /**
     * 不带时分秒的日期字符串转化
     * <p>
     * String output1 = formattedDate("2019年07月15日", "yyyy年MM月dd日", "yyyy-MM-dd");
     * String output2 = formattedDate("2019/07/16", "yyyy/MM/dd", "yyyy-MM-dd");
     * String output3 = formattedDate("2019-07-16", "yyyy-MM-dd", "yyyy/MM/dd");
     *
     * @param input        输入的日期
     * @param inputFormat  输入日期的格式
     * @param outputFormat 输出日期的格式
     * @return 输出的日期，不带时分秒
     */
    public static String formattedDate(String input, String inputFormat, String outputFormat) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        LocalDate localDate = LocalDate.parse(input, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        return localDate.format(outputFormatter);
    }

    /**
     * 带时分秒的日期字符串转换
     *
     * @param input        输入的日期
     * @param inputFormat  输入日期的格式
     * @param outputFormat 输出日期的格式
     * @return 输出指定格式的日期，可以带时分秒，也可以不带
     */
    public static String formattedDateTime(String input, String inputFormat, String outputFormat) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        LocalDateTime localDateTime = LocalDateTime.parse(input, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        return localDateTime.format(outputFormatter);
    }

    /**
     * LocalDateTime -> Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * Date -> LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }
}
