package grpc;

import demo.GreetHelloWorldGrpc;
import demo.HelloWorldRequest;
import demo.HelloWorldResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class HelloWorldClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldClient.class);

    private final ManagedChannel channel;
    private final GreetHelloWorldGrpc.GreetHelloWorldBlockingStub blockingStub;


    public HelloWorldClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = GreetHelloWorldGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String greet(String name) {
        HelloWorldRequest request = HelloWorldRequest
                .newBuilder()
                .setName(name)
                .build();
        HelloWorldResponse response = blockingStub.sayHello(request);
        String message = response.getMessage();

        LOGGER.info("response message -=> {}", message);

        return message;
    }

    public static void execute() {
        HelloWorldClient client = new HelloWorldClient("localhost", 8851);
        for (int i = 0; i < 5; i++ ) {
            System.out.println(client.greet("world: " + i));
        }
    }
}
