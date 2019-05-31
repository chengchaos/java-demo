package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2019-04-26 20:57 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
//@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String name;

    private int age;


}
