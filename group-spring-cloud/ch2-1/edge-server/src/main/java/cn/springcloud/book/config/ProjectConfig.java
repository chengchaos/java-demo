package cn.springcloud.book.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/15/2021 4:56 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "project.config")
public class ProjectConfig {

    private boolean inspectOnStart;

    public boolean isInspectOnStart() {
        return inspectOnStart;
    }

    public void setInspectOnStart(boolean inspectOnStart) {
        this.inspectOnStart = inspectOnStart;
    }
}
