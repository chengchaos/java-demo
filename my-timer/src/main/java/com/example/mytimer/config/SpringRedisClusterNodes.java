package com.example.mytimer.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
//@ConfigurationProperties(prefix="spring.redis.cluster") //接收application.yml中的myProps下面的属性  
public class SpringRedisClusterNodes {
 
    private List<String> nodes;
    
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
