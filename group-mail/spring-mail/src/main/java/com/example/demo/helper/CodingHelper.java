package com.example.demo.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class CodingHelper {

    private static final Logger logger = LoggerFactory.getLogger(CodingHelper.class);

    private CodingHelper() {
        super();
    }

    public static int[] mySort(int[] intArray) {

        Arrays.sort(intArray);
        return intArray;
    }

    public static List<String> mySort2(List<String> input) {
        Collections.sort(input);
        return input;
    }

    public static void closeAutoCloseable(AutoCloseable ac) {
        if (Objects.nonNull(ac)) {
            try {
                ac.close();
            } catch (Exception e) {
                logger.error("Closing a AutoCloseable ... and then ", e);
            }
        }
    }
}
