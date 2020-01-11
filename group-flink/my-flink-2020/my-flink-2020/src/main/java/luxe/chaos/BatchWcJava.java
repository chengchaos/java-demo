package luxe.chaos;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

/**
 * 使用 Java API 开发 Flink 的
 * 批处理应用程序
 */
public class BatchWcJava {


    public static void main(String[] args) throws Exception {

        // 1, 获取环境
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        String input = "file:///E:\\sinogold\\docs";

        // step2: 读入数据
        DataSource<String> txtDs = env.readTextFile(input);
        txtDs.print();

        // step3: 应用操作 transfrom


    }
}
