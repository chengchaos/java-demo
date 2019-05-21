package com.example.myscala002.demo;

import javafx.util.Pair;

import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/20 0020 上午 11:09 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class DateTimeDemo {


    public static Pair<LocalDateTime, LocalDateTime> splitDateFrom(LocalDateTime beginDateTime){

        LocalDate currentDate = beginDateTime.toLocalDate();
        LocalTime currentTime = beginDateTime.toLocalTime();
        LocalDateTime begin = LocalDateTime.of(currentDate, currentTime);

        LocalDateTime end = LocalDateTime.of(currentDate, LocalTime.MAX);

        return new Pair<>(begin, end);

    }

    public static Pair<LocalDateTime, LocalDateTime> splitDateTo(LocalDateTime endDateTime){

        LocalDate currentDate = endDateTime.toLocalDate();
        LocalTime currentTime = endDateTime.toLocalTime();
        LocalDateTime begin = LocalDateTime.of(currentDate, LocalTime.MIN);
        LocalDateTime end =LocalDateTime.of(currentDate, currentTime);

        return new Pair<>(begin, end);
    }

    public static Pair<LocalDateTime, LocalDateTime> splitDateWith(LocalDateTime localdateTime){

        return splitDateWith(localdateTime.toLocalDate());
    }

    public static Pair<LocalDateTime, LocalDateTime> splitDateWith(LocalDate localdate){

        LocalDateTime begin = LocalDateTime.of(localdate, LocalTime.MIN);
        LocalDateTime end =LocalDateTime.of(localdate, LocalTime.MAX);

        return new Pair<>(begin, end);
    }

    public static List<Pair<LocalDateTime, LocalDateTime>> splitDates(LocalDateTime begin,
                                                                      LocalDateTime end,
                                                                      int limit) {
        if (begin.isAfter(end)) {
            return Collections.emptyList();
        }

        LocalDate beginLocalDate = begin.toLocalDate();
        LocalDate endLocalDate = end.toLocalDate();

        if (beginLocalDate.isEqual(endLocalDate)) {
            return Collections.singletonList(new Pair<>(begin, end));
        }

        List<Pair<LocalDateTime, LocalDateTime>> resultList = new ArrayList<>(limit);
        Pair<LocalDateTime, LocalDateTime> pair1 = splitDateFrom(begin);
        resultList.add(pair1);
        LocalDate nextLoalDate = pair1.getKey().toLocalDate().plusDays(1L);

        int index = 1;
        while (index < limit && nextLoalDate.isBefore(endLocalDate)) {
            Pair<LocalDateTime, LocalDateTime> pair2 = splitDateWith(nextLoalDate);
            resultList.add(pair2);
            nextLoalDate = pair2.getKey().toLocalDate().plusDays(1L);
            index++;
        }

        if (index < limit) {
            Pair<LocalDateTime, LocalDateTime> pair3 = splitDateTo(end);
            resultList.add(pair3);
        }

        return resultList;
    }


    public static void main(String[] args) {


        LocalDateTime begin = LocalDateTime.now();
//        LocalDateTime end = begin.plusDays(0L);

        LocalDateTime end = LocalDateTime.of(begin.toLocalDate(), LocalTime.of(13, 25, 30));

        List<Pair<LocalDateTime, LocalDateTime>> first = splitDates(begin, end, 7);

        first.forEach(System.out::println);


    }
}
