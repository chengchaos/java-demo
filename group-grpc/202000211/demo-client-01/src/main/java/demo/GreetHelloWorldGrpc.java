package demo;

import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.26.0)",
        comments = "Source: helloworld.proto")
public final class GreetHelloWorldGrpc {

    private GreetHelloWorldGrpc() {}

    public static final String SERVICE_NAME = "helloworld.GreetHelloWorld";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<HelloWorldRequest,
            demo.HelloWorldResponse> getSayHelloMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "SayHello",
            requestType = HelloWorldRequest.class,
            responseType = demo.HelloWorldResponse.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<HelloWorldRequest,
            demo.HelloWorldResponse> getSayHelloMethod() {
        io.grpc.MethodDescriptor<HelloWorldRequest, demo.HelloWorldResponse> getSayHelloMethod;
        if ((getSayHelloMethod = GreetHelloWorldGrpc.getSayHelloMethod) == null) {
            synchronized (GreetHelloWorldGrpc.class) {
                if ((getSayHelloMethod = GreetHelloWorldGrpc.getSayHelloMethod) == null) {
                    GreetHelloWorldGrpc.getSayHelloMethod = getSayHelloMethod =
                            io.grpc.MethodDescriptor.<HelloWorldRequest, demo.HelloWorldResponse>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHello"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            HelloWorldRequest.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            demo.HelloWorldResponse.getDefaultInstance()))
                                    .setSchemaDescriptor(new GreetHelloWorldMethodDescriptorSupplier("SayHello"))
                                    .build();
                }
            }
        }
        return getSayHelloMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static GreetHelloWorldStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<GreetHelloWorldStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<GreetHelloWorldStub>() {
                    @Override
                    public GreetHelloWorldStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new GreetHelloWorldStub(channel, callOptions);
                    }
                };
        return GreetHelloWorldStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static GreetHelloWorldBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<GreetHelloWorldBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<GreetHelloWorldBlockingStub>() {
                    @Override
                    public GreetHelloWorldBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new GreetHelloWorldBlockingStub(channel, callOptions);
                    }
                };
        return GreetHelloWorldBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static GreetHelloWorldFutureStub newFutureStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<GreetHelloWorldFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<GreetHelloWorldFutureStub>() {
                    @Override
                    public GreetHelloWorldFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new GreetHelloWorldFutureStub(channel, callOptions);
                    }
                };
        return GreetHelloWorldFutureStub.newStub(factory, channel);
    }

    /**
     */
    public static abstract class GreetHelloWorldImplBase implements io.grpc.BindableService {

        /**
         */
        public void sayHello(HelloWorldRequest request,
                             io.grpc.stub.StreamObserver<demo.HelloWorldResponse> responseObserver) {
            asyncUnimplementedUnaryCall(getSayHelloMethod(), responseObserver);
        }

        @Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getSayHelloMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            HelloWorldRequest,
                                            demo.HelloWorldResponse>(
                                            this, METHODID_SAY_HELLO)))
                    .build();
        }
    }

    /**
     */
    public static final class GreetHelloWorldStub extends io.grpc.stub.AbstractAsyncStub<GreetHelloWorldStub> {
        private GreetHelloWorldStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected GreetHelloWorldStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new GreetHelloWorldStub(channel, callOptions);
        }

        /**
         */
        public void sayHello(HelloWorldRequest request,
                             io.grpc.stub.StreamObserver<demo.HelloWorldResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getSayHelloMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     */
    public static final class GreetHelloWorldBlockingStub extends io.grpc.stub.AbstractBlockingStub<GreetHelloWorldBlockingStub> {
        private GreetHelloWorldBlockingStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected GreetHelloWorldBlockingStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new GreetHelloWorldBlockingStub(channel, callOptions);
        }

        /**
         */
        public demo.HelloWorldResponse sayHello(HelloWorldRequest request) {
            return blockingUnaryCall(
                    getChannel(), getSayHelloMethod(), getCallOptions(), request);
        }
    }

    /**
     */
    public static final class GreetHelloWorldFutureStub extends io.grpc.stub.AbstractFutureStub<GreetHelloWorldFutureStub> {
        private GreetHelloWorldFutureStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected GreetHelloWorldFutureStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new GreetHelloWorldFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<demo.HelloWorldResponse> sayHello(
                HelloWorldRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(getSayHelloMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_SAY_HELLO = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final GreetHelloWorldImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(GreetHelloWorldImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_SAY_HELLO:
                    serviceImpl.sayHello((HelloWorldRequest) request,
                            (io.grpc.stub.StreamObserver<demo.HelloWorldResponse>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class GreetHelloWorldBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        GreetHelloWorldBaseDescriptorSupplier() {}

        @Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return HelloWorldProto.getDescriptor();
        }

        @Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("GreetHelloWorld");
        }
    }

    private static final class GreetHelloWorldFileDescriptorSupplier
            extends GreetHelloWorldBaseDescriptorSupplier {
        GreetHelloWorldFileDescriptorSupplier() {}
    }

    private static final class GreetHelloWorldMethodDescriptorSupplier
            extends GreetHelloWorldBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        GreetHelloWorldMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (GreetHelloWorldGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new GreetHelloWorldFileDescriptorSupplier())
                            .addMethod(getSayHelloMethod())
                            .build();
                }
            }
        }
        return result;
    }
}
