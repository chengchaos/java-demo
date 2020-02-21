package luxe.chaos.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentDemo {

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private String serverName;

    public String getServerName() {
        if (serverName == null) {
            serverName = environment.getProperty("project.report.server-name");
        }
        return serverName;
    }
}
