package com.example.myscala002.hbase;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/30 0030 下午 8:21 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HBaseUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseUtilTest.class);

    @Test
    public void createTableTest() {

        HBaseUtil.createTable("FileTable",
                new String[]{
                        "fileInfo", "saveInfo"
                });

        LOGGER.info(">> end >>");

    }

    @Test
    public void addFileDetails() {

        HBaseUtil.putRow("FileTable", "rowkey1",
                "fileInfo", "name","file1.txt");
        HBaseUtil.putRow("FileTable", "rowkey1",
                "fileInfo", "type","txt");
        HBaseUtil.putRow("FileTable", "rowkey1",
                "fileInfo", "size","1024");
        HBaseUtil.putRow("FileTable", "rowkey1",
                "saveInfo", "creator","20190401");

        HBaseUtil.putRow("FileTable", "rowkey2",
                "fileInfo", "name","file1.txt");
        HBaseUtil.putRow("FileTable", "rowkey2",
                "fileInfo", "type","txt");
        HBaseUtil.putRow("FileTable", "rowkey2",
                "fileInfo", "size","1024");
        HBaseUtil.putRow("FileTable", "rowkey2",
                "saveInfo", "creator","20190401");

    }

    @Test
    public void valueFilterTest() {

        Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("Wang"));

        ResultScanner scanner = HBaseUtil.getScanner("mytable", "row1", "row100", new FilterList(filter));

        for (Result result :scanner) {
            String str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("name")));
            LOGGER.info("str ==> {}", str);
        }

        scanner.close();
        LOGGER.info("== end ==");

    }
}
