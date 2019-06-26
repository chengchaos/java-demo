package com.example.myscala002.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CanUnbuffer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hdfs.DistributedFileSystem;
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
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 3:49 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class ValueFilterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueFilterTest.class);


    private Configuration createConfiguration() {

        Configuration config = HBaseConfiguration.create();
        try {

            // 添加必要的配置文件： hbase-site.xml, core-site.xml
            config.addResource(new Path(ClassLoader.getSystemResource("hbase-site.xml").toURI()));
            config.addResource(new Path(ClassLoader.getSystemResource("core-site.xml").toURI()));
            return config;
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void valueFilterTest() {

        DistributedFileSystem distributedFileSystem;
        CanUnbuffer canUnbuffer;
        // org.apache.hadoop.hdfs.DistributedFileSystem
        try (Connection connection = ConnectionFactory.createConnection(createConfiguration())) {

            Table table = connection.getTable(TableName.valueOf("mytable"));

            Scan scan = new Scan();
            Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("Wang"));

            scan.setFilter(filter);

            ResultScanner rs = table.getScanner(scan);
            for (Result result: rs) {
                String str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("name")));
                LOGGER.info("str ==> {}", str);
            }

        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

}
