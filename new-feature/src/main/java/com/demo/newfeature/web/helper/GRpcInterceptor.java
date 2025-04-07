package com.demo.newfeature.web.helper;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.micrometer.common.util.StringUtils;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class GRpcInterceptor implements ServerInterceptor {

    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");
    private static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {


        String token = metadata.get(AUTHORIZATION_METADATA_KEY);

        if (StringUtils.isBlank(token)) {
            serverCall.close(Status.ABORTED, metadata);
            return new ServerCall.Listener<ReqT>() {
                // noop
            };
        }

        Context ctx = Context.current()
                .withValue(CLIENT_ID_CONTEXT_KEY, token);
        return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
    }
}
