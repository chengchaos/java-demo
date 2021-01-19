package cn.springcloud.book.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/14/2021 11:12 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Document
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long id;

    @Field("role_name")
    private String roleName;

    @Field("note")
    private String note;
}
