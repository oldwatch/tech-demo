package com.demo.newfeature.web.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class gRPCDemoGrpc {

  private gRPCDemoGrpc() {}

  public static final java.lang.String SERVICE_NAME = "gRPCDemo";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.demo.newfeature.web.grpc.proto.AddOneRequest,
      com.demo.newfeature.web.grpc.proto.OneVO> getAddEntryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addEntry",
      requestType = com.demo.newfeature.web.grpc.proto.AddOneRequest.class,
      responseType = com.demo.newfeature.web.grpc.proto.OneVO.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.demo.newfeature.web.grpc.proto.AddOneRequest,
      com.demo.newfeature.web.grpc.proto.OneVO> getAddEntryMethod() {
    io.grpc.MethodDescriptor<com.demo.newfeature.web.grpc.proto.AddOneRequest, com.demo.newfeature.web.grpc.proto.OneVO> getAddEntryMethod;
    if ((getAddEntryMethod = gRPCDemoGrpc.getAddEntryMethod) == null) {
      synchronized (gRPCDemoGrpc.class) {
        if ((getAddEntryMethod = gRPCDemoGrpc.getAddEntryMethod) == null) {
          gRPCDemoGrpc.getAddEntryMethod = getAddEntryMethod =
              io.grpc.MethodDescriptor.<com.demo.newfeature.web.grpc.proto.AddOneRequest, com.demo.newfeature.web.grpc.proto.OneVO>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addEntry"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.demo.newfeature.web.grpc.proto.AddOneRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.demo.newfeature.web.grpc.proto.OneVO.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCDemoMethodDescriptorSupplier("addEntry"))
              .build();
        }
      }
    }
    return getAddEntryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.demo.newfeature.web.grpc.proto.SearchRequest,
      com.demo.newfeature.web.grpc.proto.OneVO> getSearchByNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchByName",
      requestType = com.demo.newfeature.web.grpc.proto.SearchRequest.class,
      responseType = com.demo.newfeature.web.grpc.proto.OneVO.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.demo.newfeature.web.grpc.proto.SearchRequest,
      com.demo.newfeature.web.grpc.proto.OneVO> getSearchByNameMethod() {
    io.grpc.MethodDescriptor<com.demo.newfeature.web.grpc.proto.SearchRequest, com.demo.newfeature.web.grpc.proto.OneVO> getSearchByNameMethod;
    if ((getSearchByNameMethod = gRPCDemoGrpc.getSearchByNameMethod) == null) {
      synchronized (gRPCDemoGrpc.class) {
        if ((getSearchByNameMethod = gRPCDemoGrpc.getSearchByNameMethod) == null) {
          gRPCDemoGrpc.getSearchByNameMethod = getSearchByNameMethod =
              io.grpc.MethodDescriptor.<com.demo.newfeature.web.grpc.proto.SearchRequest, com.demo.newfeature.web.grpc.proto.OneVO>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.demo.newfeature.web.grpc.proto.SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.demo.newfeature.web.grpc.proto.OneVO.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCDemoMethodDescriptorSupplier("searchByName"))
              .build();
        }
      }
    }
    return getSearchByNameMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static gRPCDemoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gRPCDemoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gRPCDemoStub>() {
        @java.lang.Override
        public gRPCDemoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gRPCDemoStub(channel, callOptions);
        }
      };
    return gRPCDemoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static gRPCDemoBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gRPCDemoBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gRPCDemoBlockingV2Stub>() {
        @java.lang.Override
        public gRPCDemoBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gRPCDemoBlockingV2Stub(channel, callOptions);
        }
      };
    return gRPCDemoBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static gRPCDemoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gRPCDemoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gRPCDemoBlockingStub>() {
        @java.lang.Override
        public gRPCDemoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gRPCDemoBlockingStub(channel, callOptions);
        }
      };
    return gRPCDemoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static gRPCDemoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gRPCDemoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gRPCDemoFutureStub>() {
        @java.lang.Override
        public gRPCDemoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gRPCDemoFutureStub(channel, callOptions);
        }
      };
    return gRPCDemoFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    default void addEntry(com.demo.newfeature.web.grpc.proto.AddOneRequest request,
        io.grpc.stub.StreamObserver<com.demo.newfeature.web.grpc.proto.OneVO> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddEntryMethod(), responseObserver);
    }

    /**
     */
    default void searchByName(com.demo.newfeature.web.grpc.proto.SearchRequest request,
        io.grpc.stub.StreamObserver<com.demo.newfeature.web.grpc.proto.OneVO> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchByNameMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service gRPCDemo.
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class gRPCDemoImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return gRPCDemoGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service gRPCDemo.
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class gRPCDemoStub
      extends io.grpc.stub.AbstractAsyncStub<gRPCDemoStub> {
    private gRPCDemoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCDemoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gRPCDemoStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void addEntry(com.demo.newfeature.web.grpc.proto.AddOneRequest request,
        io.grpc.stub.StreamObserver<com.demo.newfeature.web.grpc.proto.OneVO> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddEntryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchByName(com.demo.newfeature.web.grpc.proto.SearchRequest request,
        io.grpc.stub.StreamObserver<com.demo.newfeature.web.grpc.proto.OneVO> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSearchByNameMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service gRPCDemo.
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class gRPCDemoBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<gRPCDemoBlockingV2Stub> {
    private gRPCDemoBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCDemoBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gRPCDemoBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.demo.newfeature.web.grpc.proto.OneVO addEntry(com.demo.newfeature.web.grpc.proto.AddOneRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddEntryMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, com.demo.newfeature.web.grpc.proto.OneVO>
        searchByName(com.demo.newfeature.web.grpc.proto.SearchRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getSearchByNameMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service gRPCDemo.
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class gRPCDemoBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<gRPCDemoBlockingStub> {
    private gRPCDemoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCDemoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gRPCDemoBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.demo.newfeature.web.grpc.proto.OneVO addEntry(com.demo.newfeature.web.grpc.proto.AddOneRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddEntryMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.demo.newfeature.web.grpc.proto.OneVO> searchByName(
        com.demo.newfeature.web.grpc.proto.SearchRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSearchByNameMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service gRPCDemo.
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class gRPCDemoFutureStub
      extends io.grpc.stub.AbstractFutureStub<gRPCDemoFutureStub> {
    private gRPCDemoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCDemoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gRPCDemoFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.demo.newfeature.web.grpc.proto.OneVO> addEntry(
        com.demo.newfeature.web.grpc.proto.AddOneRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddEntryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_ENTRY = 0;
  private static final int METHODID_SEARCH_BY_NAME = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_ENTRY:
          serviceImpl.addEntry((com.demo.newfeature.web.grpc.proto.AddOneRequest) request,
              (io.grpc.stub.StreamObserver<com.demo.newfeature.web.grpc.proto.OneVO>) responseObserver);
          break;
        case METHODID_SEARCH_BY_NAME:
          serviceImpl.searchByName((com.demo.newfeature.web.grpc.proto.SearchRequest) request,
              (io.grpc.stub.StreamObserver<com.demo.newfeature.web.grpc.proto.OneVO>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getAddEntryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.demo.newfeature.web.grpc.proto.AddOneRequest,
              com.demo.newfeature.web.grpc.proto.OneVO>(
                service, METHODID_ADD_ENTRY)))
        .addMethod(
          getSearchByNameMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.demo.newfeature.web.grpc.proto.SearchRequest,
              com.demo.newfeature.web.grpc.proto.OneVO>(
                service, METHODID_SEARCH_BY_NAME)))
        .build();
  }

  private static abstract class gRPCDemoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    gRPCDemoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.demo.newfeature.web.grpc.proto.Demo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("gRPCDemo");
    }
  }

  private static final class gRPCDemoFileDescriptorSupplier
      extends gRPCDemoBaseDescriptorSupplier {
    gRPCDemoFileDescriptorSupplier() {}
  }

  private static final class gRPCDemoMethodDescriptorSupplier
      extends gRPCDemoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    gRPCDemoMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (gRPCDemoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new gRPCDemoFileDescriptorSupplier())
              .addMethod(getAddEntryMethod())
              .addMethod(getSearchByNameMethod())
              .build();
        }
      }
    }
    return result;
  }
}
