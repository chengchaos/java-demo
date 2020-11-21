package vip.chengchao.by2020;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class JavaDataSetDataSourceApp {

    public static void main(String[] args) {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        fromCollection(env);

        textFile(env);

    }


    private static void fromCollection(ExecutionEnvironment env) {
        DataSource<Integer> integerDataSource = env.fromCollection(Arrays.asList(1, 2, 3, 4, 5));
        try {
            integerDataSource.print();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static void textFile(ExecutionEnvironment env) {

        Path path = Paths.get("src/test/resources/");
        DataSet<String> dataSet = env.readTextFile(path.toUri().toString());
        try {
            dataSet.print();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }
}
