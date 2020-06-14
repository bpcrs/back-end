package generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: invoke.proto")
public final class InvokeServiceGrpc {

  private InvokeServiceGrpc() {}

  public static final String SERVICE_NAME = "proto.InvokeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generated.InvokeProto.InvokeRequest,
      generated.InvokeProto.InvokeResponse> getSendInvokeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendInvoke",
      requestType = generated.InvokeProto.InvokeRequest.class,
      responseType = generated.InvokeProto.InvokeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.InvokeProto.InvokeRequest,
      generated.InvokeProto.InvokeResponse> getSendInvokeMethod() {
    io.grpc.MethodDescriptor<generated.InvokeProto.InvokeRequest, generated.InvokeProto.InvokeResponse> getSendInvokeMethod;
    if ((getSendInvokeMethod = InvokeServiceGrpc.getSendInvokeMethod) == null) {
      synchronized (InvokeServiceGrpc.class) {
        if ((getSendInvokeMethod = InvokeServiceGrpc.getSendInvokeMethod) == null) {
          InvokeServiceGrpc.getSendInvokeMethod = getSendInvokeMethod =
              io.grpc.MethodDescriptor.<generated.InvokeProto.InvokeRequest, generated.InvokeProto.InvokeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendInvoke"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.InvokeProto.InvokeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.InvokeProto.InvokeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InvokeServiceMethodDescriptorSupplier("sendInvoke"))
              .build();
        }
      }
    }
    return getSendInvokeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InvokeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokeServiceStub>() {
        @java.lang.Override
        public InvokeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokeServiceStub(channel, callOptions);
        }
      };
    return InvokeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InvokeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokeServiceBlockingStub>() {
        @java.lang.Override
        public InvokeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokeServiceBlockingStub(channel, callOptions);
        }
      };
    return InvokeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InvokeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokeServiceFutureStub>() {
        @java.lang.Override
        public InvokeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokeServiceFutureStub(channel, callOptions);
        }
      };
    return InvokeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class InvokeServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sendInvoke(generated.InvokeProto.InvokeRequest request,
        io.grpc.stub.StreamObserver<generated.InvokeProto.InvokeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSendInvokeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendInvokeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.InvokeProto.InvokeRequest,
                generated.InvokeProto.InvokeResponse>(
                  this, METHODID_SEND_INVOKE)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class InvokeServiceStub extends io.grpc.stub.AbstractAsyncStub<InvokeServiceStub> {
    private InvokeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sendInvoke(generated.InvokeProto.InvokeRequest request,
        io.grpc.stub.StreamObserver<generated.InvokeProto.InvokeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendInvokeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class InvokeServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<InvokeServiceBlockingStub> {
    private InvokeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public generated.InvokeProto.InvokeResponse sendInvoke(generated.InvokeProto.InvokeRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendInvokeMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class InvokeServiceFutureStub extends io.grpc.stub.AbstractFutureStub<InvokeServiceFutureStub> {
    private InvokeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.InvokeProto.InvokeResponse> sendInvoke(
        generated.InvokeProto.InvokeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendInvokeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_INVOKE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InvokeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InvokeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_INVOKE:
          serviceImpl.sendInvoke((generated.InvokeProto.InvokeRequest) request,
              (io.grpc.stub.StreamObserver<generated.InvokeProto.InvokeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class InvokeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InvokeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.InvokeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InvokeService");
    }
  }

  private static final class InvokeServiceFileDescriptorSupplier
      extends InvokeServiceBaseDescriptorSupplier {
    InvokeServiceFileDescriptorSupplier() {}
  }

  private static final class InvokeServiceMethodDescriptorSupplier
      extends InvokeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    InvokeServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InvokeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InvokeServiceFileDescriptorSupplier())
              .addMethod(getSendInvokeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
