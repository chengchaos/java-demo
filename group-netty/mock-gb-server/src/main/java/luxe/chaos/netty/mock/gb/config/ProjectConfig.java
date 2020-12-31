package luxe.chaos.netty.mock.gb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {

    private int nettyPort;

    private boolean nettyAutoStart;

    private boolean kafkaAutoStart;

    public int getNettyPort() {
        return nettyPort;
    }

    public void setNettyPort(int nettyPort) {
        this.nettyPort = nettyPort;
    }

    public boolean isNettyAutoStart() {
        return nettyAutoStart;
    }

    public void setNettyAutoStart(boolean nettyAutoStart) {
        this.nettyAutoStart = nettyAutoStart;
    }

    public boolean isKafkaAutoStart() {
        return kafkaAutoStart;
    }

    public void setKafkaAutoStart(boolean kafkaAutoStart) {
        this.kafkaAutoStart = kafkaAutoStart;
    }
}
