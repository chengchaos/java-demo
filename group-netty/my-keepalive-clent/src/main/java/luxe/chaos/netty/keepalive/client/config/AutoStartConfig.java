package luxe.chaos.netty.keepalive.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "project.auto-start")
@Component
//@EnableConfigurationProperties
public class AutoStartConfig {

    private boolean kafka;
    private boolean netty;

    public boolean isKafka() {
        return kafka;
    }

    public void setKafka(boolean kafka) {
        this.kafka = kafka;
    }

    public boolean isNetty() {
        return netty;
    }

    public void setNetty(boolean netty) {
        this.netty = netty;
    }
}
