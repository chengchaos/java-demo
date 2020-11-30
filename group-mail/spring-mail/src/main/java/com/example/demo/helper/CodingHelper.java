package com.example.demo.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodingHelper {



    public static int[] mySort(int[] intArray) {

        Arrays.sort(intArray);
        return intArray;
    }

    public static List<String> mySort2(List<String> input) {
        Collections.sort(input);
        return input;
    }
}
