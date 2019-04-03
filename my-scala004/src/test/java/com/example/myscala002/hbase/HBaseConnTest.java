package com.example.myscala002.hbase;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/30 0030 下午 7:30 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HBaseConnTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseConnTest.class);

    @Test
    public void getConnTest() {

        Connection conn = HBaseConn.getHBaseConn();

        LOGGER.info("conn ==> {}", conn.isClosed());

        HBaseConn.closeConn();
    }

    @Test
    public void getTableTest() {

        String tableName = "US_POPULATION";
        try {
            Table table = HBaseConn.getTable(tableName);
            LOGGER.info("tableName ==> {}, ==> {}", tableName, table.getName().getNameAsString());


            table.close();
        } catch (IOException e) {
            LOGGER.error("", e);
        }

    }
}
