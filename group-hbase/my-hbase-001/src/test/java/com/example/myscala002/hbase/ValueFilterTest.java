package com.example.myscala002.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CanUnbuffer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.function.Consumer;

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


        // org.apache.hadoop.hdfs.DistributedFileSystem
        try (Connection connection = ConnectionFactory.createConnection(createConfiguration())) {

            Table table = connection.getTable(TableName.valueOf("mytable"));

            Scan scan = new Scan();
            Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("Wang"));

            scan.setFilter(filter);

            try (ResultScanner rs = table.getScanner(scan)) {

                String str;
                for (Result result: rs) {
                    str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("name")));
                    LOGGER.info("mycf:name ==> {}", str);
                    str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("teacher")));
                    LOGGER.info("mycf:teacher ==> {}", str);
                }
            }


        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }



    private void withTable(String tableName, Consumer<Table> tableConsumer) {

        Configuration conf = this.createConfiguration();
        try (Connection conn = ConnectionFactory.createConnection(conf)) {
            Table table = conn.getTable(TableName.valueOf(Bytes.toBytes(tableName)));
            tableConsumer.accept(table);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }


    @Test
    public void singleColumnValueFilterTest() {


        Filter filter = new SingleColumnValueFilter(Bytes.toBytes("mycf"),
                Bytes.toBytes("name"),
                CompareFilter.CompareOp.EQUAL,
                new SubstringComparator("Wang"));

        final Scan scan = new Scan().setFilter(filter);

        this.withTable("mytable", table -> {

            try ( ResultScanner rs = table.getScanner(scan)) {

                for (Result result: rs) {
                    String str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("teacher")));
                    LOGGER.info("str ==> {}", str);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        });

    }


    @Test
    public void filterListTest01() {

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        /* 只有列族为 mycf 的记录才放入结果集 */
        Filter familyFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("mycf")));

        filterList.addFilter(familyFilter);
        /*只有列为 teacher 的记录才放入结果集 */

        Filter colFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("teacher")));
        filterList.addFilter(colFilter);

        /* 只有包含 Wang 的记录才放入结果集 */
        Filter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL,
                new SubstringComparator("Wang"));
        filterList.addFilter(valueFilter);

        Scan scan = new Scan().setFilter(filterList);

        this.withTable("mytable", table ->{
            try (ResultScanner rs = table.getScanner(scan)) {

                for (Result result :rs) {
                    String str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("teacher")));
                    LOGGER.info("mycf:teacher ==> {}", str);
                }
            } catch (IOException e){ {

                LOGGER.error("", e);
            }}
        });
    }



    @Test
    public void singleColumnValueFilterTest02() {


        Filter filter = new SingleColumnValueFilter(Bytes.toBytes("mycf"),
                Bytes.toBytes("name"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("Wang")));

        final Scan scan = new Scan().setFilter(filter);

        this.withTable("mytable", table -> {

            try ( ResultScanner rs = table.getScanner(scan)) {

                for (Result result: rs) {
                    String str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("teacher")));
                    LOGGER.info("str ==> {}", str);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        });

    }


    @Test
    public void PageFilterTest() {

        Filter filter = new PageFilter(2);

        final Scan scan = new Scan().setFilter(filter);

        this.withTable("mytable", table -> {

            try ( ResultScanner rs = table.getScanner(scan)) {

                for (Result result: rs) {
                    String str = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("name")));
                    String rowkey = Bytes.toString(result.getRow());
                    LOGGER.info("rowkey ==>{}, mycf:name ==> {}", rowkey, str);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        });


    }

    private byte[] printResult(ResultScanner rs) {

        byte[] lastRowKey = null;

        for (Result result: rs) {
            byte[] rowKey = result.getRow();
            String name = Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("name")));
            LOGGER.info("name ==> {}", name);
            lastRowKey = rowKey;
        }

        return lastRowKey;
    }



    @Test
    public void PageFilterTest02() {


        Filter filter = new PageFilter(2);

        final Scan scan = new Scan().setFilter(filter);

        this.withTable("mytable", table -> {

            byte[] lastRowKey;

            try ( ResultScanner rs = table.getScanner(scan)) {

                lastRowKey = this.printResult(rs);

                // 第二页
                byte[] startRowKey = Bytes.add(lastRowKey, new byte[1]);
                scan.setStartRow(startRowKey);


            } catch (IOException e) {
                LOGGER.error("", e);
            }

            // 第二页
            try ( ResultScanner rs = table.getScanner(scan)) {

                lastRowKey = this.printResult(rs);

                byte[] startRowKey = Bytes.add(lastRowKey, new byte[1]);
                scan.setStartRow(startRowKey);


            } catch (IOException e) {
                LOGGER.error("", e);
            }
        });


    }
}
