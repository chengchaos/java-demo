package luxe.chaos.demo.grpc;

import demo.GreetHelloWorldGrpc;
import demo.HelloWorldRequest;
import demo.HelloWorldResponse;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloWorldServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldServer.class);

    private int port = 8851;
    private Server server;

    private HelloWorldServer start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService((BindableService) new GreeterHelloWroldImpl())
                .build()
                .start();
        LOGGER.info("service start ..... ");

        Runtime.getRuntime()
                .addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        LOGGER.info("*** Shuting down gRPC server since JVM is shutting down");
                        HelloWorldServer.this.stop();
                        LOGGER.info("*** Done .");
                    }
                });
        return this;
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void serverStart() {

        final HelloWorldServer server = new HelloWorldServer();
        try {
            server.start().blockUntilShutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class GreeterHelloWroldImpl extends GreetHelloWorldGrpc.GreetHelloWorldImplBase {

        @Override
        public void sayHello(HelloWorldRequest request, StreamObserver<HelloWorldResponse> responseObserver) {
            LOGGER.info("service: -=> {}", request.getName());
            HelloWorldResponse reply = HelloWorldResponse.newBuilder()
                    .setMessage("Hello " + request.getName())
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

}
