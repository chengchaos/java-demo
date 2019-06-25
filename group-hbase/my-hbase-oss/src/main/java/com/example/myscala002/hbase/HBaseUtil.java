package com.example.myscala002.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.apache.hadoop.hbase.util.Bytes.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/30 0030 下午 7:45 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HBaseUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(HBaseUtil.class);

    /**
     * 创建 HBase 表
     *
     * @param tableName 表名称
     * @param cfs 列族的数组
     * @return 是否成功
     */
    public static boolean createTable(String tableName, String[] cfs) {

        try (HBaseAdmin admin = (HBaseAdmin) HBaseConn.getHBaseConn().getAdmin()) {

            if (admin.tableExists(tableName)) {
                return false;
            }

            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

            Arrays.stream(cfs).forEach(cf -> {
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf);
                columnDescriptor.setMaxVersions(1);
                tableDescriptor.addFamily(columnDescriptor);

            });

            admin.createTable(tableDescriptor);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        return false;
    }

    public static boolean deleteTable(String tableName) {

        try (HBaseAdmin admin = (HBaseAdmin) HBaseConn.getHBaseConn().getAdmin()) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            return true;
        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return false;
    }


    /**
     * HBase 插入一条数据
     *
     * @param tableName 表名
     * @param rowKey 唯一标识
     * @param cfName 列族名
     * @param qulaifier 列标识
     * @param data 数据
     * @return
     */
    public static boolean putRow(String tableName, String rowKey,
                                 String cfName, String qulaifier,
                                 String data) {

        try (Table table = HBaseConn.getTable(tableName)) {

            Put put = new Put(toBytes(rowKey));
            put.addColumn(toBytes(cfName), toBytes(qulaifier), toBytes(data));

            table.put(put);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return false;
    }

    public static boolean pubRows(String tableName, List<Put> puts) {

        try (Table table = HBaseConn.getTable(tableName)) {


            table.put(puts);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return false;
    }


    /**
     *
     * @param tableName 表名称
     * @param rowKey RowKey
     * @return Result
     */
    public static Result getRow(String tableName, String rowKey) {

        try (Table table = HBaseConn.getTable(tableName)) {

            Get get = new Get(toBytes(rowKey));
            return table.get(get);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return null;
    }

    public static Result getRow(String tableName, String rowKey,
                                FilterList filterList) {


        try (Table table = HBaseConn.getTable(tableName)) {

            Get get = new Get(toBytes(rowKey));
            get.setFilter(filterList);

            return table.get(get);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return null;
    }

    public static ResultScanner getScanner(String tableName ) {

        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setCaching(1000);

            return table.getScanner(scan);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return null;
    }

    /**
     *
     * @param tableName 表名
     * @param startRowKey 起始 RowKey
     * @param endRowKey 终止 RowKey
     */
    public static ResultScanner getScanner(String tableName, String startRowKey, String endRowKey) {

        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setStartRow(toBytes(startRowKey));
            scan.setStopRow(toBytes(endRowKey));
            scan.setCaching(1000);

            return table.getScanner(scan);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return null;
    }

    public static ResultScanner getScanner(String tableName, String startRowKey,
                                           String endRowKey,
                                           FilterList filterList) {

        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setStartRow(toBytes(startRowKey));
            scan.setStopRow(toBytes(endRowKey));
            scan.setFilter(filterList);
            scan.setCaching(1000);

            return table.getScanner(scan);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return null;
    }

    /**
     *
     * @param tableName 表名
     * @param rowKey RowKey
     * @return
     */
    public static boolean deleteRow(String tableName, String rowKey) {

        try (Table table = HBaseConn.getTable(tableName)) {

            Delete delete = new Delete(toBytes(rowKey));
            table.delete(delete);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return false;
    }

    public static boolean deleteColumnFamily(String tableName, String cfName) {


        try (HBaseAdmin admin = (HBaseAdmin) HBaseConn.getHBaseConn().getAdmin()) {

            admin.deleteColumn(tableName, cfName);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        return false;
    }

    public static boolean deleteQualifier(String tableName, String rowKey,
                                          String cfName, String qualifier) {

        try (Table table = HBaseConn.getTable(tableName)) {

            Delete delete = new Delete(toBytes(rowKey));
            delete.addColumn(toBytes(cfName), toBytes(qualifier));
            table.delete(delete);

        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return false;
    }
}
