package com.example.myscala002.config;

import javafx.util.Pair;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/5/21 0021 下午 1:58 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HbaseConfig {

    public static Pair<String, String> quorum =
            //new Pair<>("hbase.zookeeper.quorum", "127.0.0.1:2181");
            new Pair<>("hbase.zookeeper.quorum", "192.168.88.161:2181");


}
