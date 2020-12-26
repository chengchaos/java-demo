package luxe.chaos.netty.keepalive.client.business;

import luxe.chaos.netty.keepalive.client.config.NettyConfig;
import luxe.chaos.netty.keepalive.client.handler.EchoClient;
import luxe.chaos.netty.keepalive.client.handler.EchoClientHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DataManager implements ApplicationContextAware {




    private EchoClient echoClient;

    private ApplicationContext context;


    private final NettyConfig nettyConfig;

    private final EchoClientHandler echoClientHandler;

    @Autowired
    public DataManager(NettyConfig nettyConfig, EchoClientHandler echoClientHandler) {
        this.nettyConfig = nettyConfig;
        echoClientHandler.setDataManager(this);
        this.echoClientHandler = echoClientHandler;

    }

    public DataSender newDataSender() {
        return new DataSender(this);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    public void connect() {
        this.echoClient = new EchoClient(echoClientHandler);
        echoClient.connect(nettyConfig.getPort());
    }

    public void reConnect() {
        this.echoClient.close();
        this.echoClient = null;
        this.connect();
    }



}
