package com.example.myscala002.hbase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 10:51 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class PhonenxTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(PhonenxTest.class);


    private static final String URL = "jdbc:phoenix:mycentos7:2181";


    private void withConn(Consumer<Connection> connectionConsumer) {
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(URL )) {

            connectionConsumer.accept(conn);
        } catch (Exception e) {
            LOGGER.error("",e);
        }

    }

    @Test
    public void test001() {


        String sql = "select name from person";

        this.withConn(conn -> {

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet resultSet = ps.executeQuery()
            ) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    System.out.println("name ===> "+ name
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }
}
