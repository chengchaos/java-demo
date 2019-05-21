package com.example.myscala002.hbase;

import com.example.myscala002.config.HbaseConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/30 0030 下午 7:21 <br />
 * @since 1.1.0
 */
public class HBaseConn {



    private static final HBaseConn INSTANCE = new HBaseConn();

    private Configuration configuration;

    private Connection connection;


    private HBaseConn() {
        super();
        configuration = HBaseConfiguration.create();
        configuration.set(HbaseConfig.quorum.getKey(), HbaseConfig.quorum.getValue());
    }

    private Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }



    public void closeConn() {
        closeConn(connection);
    }

    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getHBaseConn() {
        return INSTANCE.getConnection();
    }

    public static Table getTable(String tableName) throws IOException {
        return INSTANCE.getConnection()
                .getTable(TableName.valueOf(tableName));
    }
}
