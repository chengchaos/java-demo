package luxe.chaos.netty.keepalive.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 依赖 spring-boot-configuration-processor
 */
@Configuration
@EnableConfigurationProperties
public class ProjectConfig {


    private AutoStartConfig autoStartConfig;

    private NettyConfig nettyConfig;

    public AutoStartConfig getAutoStartConfig() {
        return autoStartConfig;
    }
    @Autowired
    public void setAutoStartConfig(AutoStartConfig autoStartConfig) {
        this.autoStartConfig = autoStartConfig;
    }

    public NettyConfig getNettyConfig() {
        return nettyConfig;
    }

    @Autowired
    public void setNettyConfig(NettyConfig nettyConfig) {
        this.nettyConfig = nettyConfig;
    }
}
