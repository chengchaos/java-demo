package luxe.chaos.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "project.report")
public class ReportProperties {

    private String serverName;

    private List<Hosts> hosts;

    public static class Hosts {
        String host;
        Integer port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<Hosts> getHosts() {
        return hosts;
    }

    public void setHosts(List<Hosts> hosts) {
        this.hosts = hosts;
    }
}
