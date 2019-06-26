package cn.futuremove.tsp.vehicle.control.business;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;

import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.OutgoingConnection;
import akka.http.javadsl.model.*;
import akka.http.javadsl.model.headers.RawHeader;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class AkkaHttpClientTest {

    static final Logger LOGGER = LoggerFactory.getLogger(AkkaHttpClientTest.class);


    static final ActorSystem system;

    /**
     * Materializer 会在内部创建 actor 来运行 Stream
     * 然后通过 HTTP 对象 绑定地址和端口.
     */
    static final Materializer materializer;

    static {
        system = ActorSystem.create("AkkaHttpClientTest");
        materializer = ActorMaterializer.create(system);
    }


    public void waiting(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() {

        Flow<HttpRequest, HttpResponse, CompletionStage<OutgoingConnection>> connFlow =
                Http.get(system)
                .outgoingConnection(ConnectHttp.toHost("www.baidu.com", 80));


        Sink<HttpResponse, CompletionStage<HttpResponse>> head = Sink.<HttpResponse>head();

        HttpRequest httpRequest = HttpRequest.create("/kao")
                .withMethod(HttpMethods.GET)
                .addHeader(RawHeader.create("hello", "world"));

        CompletionStage<HttpResponse> respFuture = Source.single(httpRequest)
                .via(connFlow)
                .runWith(head, materializer);

//        respFuture.thenAccept(response -> {
//            LOGGER.info("response : {}", response);
//            StatusCode status = response.status();
//            LOGGER.info("status: {}", status);
//            response.getHeaders().iterator().forEachRemaining(httpHeader -> {
//                LOGGER.info("name : {} ==> {}", httpHeader.name(),httpHeader.value());
//            });
//            response.entity()
//                    .getDataBytes()
//                    .runWith(Sink.foreach(connect -> {
//                        String string = connect.utf8String();
//                        System.out.println("string >>>>" + string.trim().substring(0, 20) + " ...");
//                        Assert.assertTrue(StringUtil.isNotBlank(string));
//                    }), materializer);
//        });

        System.out.println("============");

        try {
            HttpResponse httpResponse = respFuture.toCompletableFuture().get(10L, TimeUnit.SECONDS);
            LOGGER.info("status: {}", httpResponse.status());

            ResponseEntity entity = httpResponse.entity();

            System.out.println("entity: ===> "+ entity.getClass());
            Source<ByteString, Object> dataBytes = entity.getDataBytes();

            Sink<Object, CompletionStage<Object>> sink2 = Sink.<Object>head();

//            Flow<ByteString, String, CompletionStage<OutgoingConnection>> flow2 =
//                    Flow.fromFunction((byteString) -> byteString.utf8String());
//
//            dataBytes.single(dataBytes)
//                    .via(flow2)
//                    .runWith(sink2);
            CompletionStage<ByteString> byteStringCompletionStage = dataBytes.runWith(Sink.head(), materializer);

            ByteString byteString = byteStringCompletionStage.toCompletableFuture()
                    .get(5L, TimeUnit.SECONDS);

            System.out.println("++++++++++" + byteString.utf8String().trim().substring(0, 50));


//            CompletionStage<Done> doneCompletionStage = dataBytes.runWith(Sink.foreach(bs -> {
//
//                String result = bs.utf8String();
//                System.out.println(">>>> " +result.length());
//
//
//            }), materializer);
//
//            Done done = doneCompletionStage.toCompletableFuture().get(10L, TimeUnit.SECONDS);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>...");


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        waiting(3L);

        System.out.println("== END ==");

    }
}
