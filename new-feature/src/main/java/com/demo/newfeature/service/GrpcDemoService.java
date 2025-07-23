package com.demo.newfeature.service;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.management.DemoManagement;
import com.demo.newfeature.web.grpc.proto.AddOneRequest;
import com.demo.newfeature.web.grpc.proto.OneVO;
import com.demo.newfeature.web.grpc.proto.SearchRequest;
import com.demo.newfeature.web.grpc.proto.gRPCDemoGrpc;
import com.demo.newfeature.web.helper.GRpcInterceptor;
import io.grpc.Context;
import org.demo.idconvert.IdEncodeTool;
import org.demo.utils.DatetimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcDemoService extends gRPCDemoGrpc.gRPCDemoImplBase {

    private static Logger log = LoggerFactory.getLogger(GrpcDemoService.class);
    private final DemoManagement management;

    private final IdEncodeTool idEncodeTool;


    public GrpcDemoService(DemoManagement management, IdEncodeTool idEncodeTool) {
        this.management = management;
        this.idEncodeTool = idEncodeTool;
    }

    public void addEntry(AddOneRequest request,
                         io.grpc.stub.StreamObserver<OneVO> responseObserver) {

        var user = GRpcInterceptor.CLIENT_ID_CONTEXT_KEY.get(Context.current());
        var rec = new OneRec(request, user);

        var result = management.addOneEntity(rec);

        var builder = OneVO.newBuilder()
                .setCreatedDate(DatetimeUtils.getTimestamp(result.commFields().createdDate()))
                .setDecValue(result.decValue())
                .setIntValue(result.intValue())
                .setCreatedBy(result.commFields().createdBy());
        if (result.submitTime() != null) {
            builder.setSubmitTime(DatetimeUtils.getTimestamp(result.submitTime()));
        }
        var vo = builder.setName(result.name())
                .setId(idEncodeTool.encode(result.id()))
                .build();
        responseObserver.onNext(vo);
        responseObserver.onCompleted();
    }


    /**
     *
     */
    public void searchByName(SearchRequest request,
                             io.grpc.stub.StreamObserver<OneVO> responseObserver) {

        var recList = management.queryByNameWild(request.getName(), idEncodeTool.decodePageToken(request.getToken()));

        recList.forEach(rec -> {
            var vo = OneVO.newBuilder()
                    .setCreatedBy(rec.commFields().createdBy())
                    .setDecValue(rec.decValue())
                    .setName(rec.name())
                    .setIntValue(rec.intValue())
                    .setId(idEncodeTool.encode(rec.id()))
                    .setSubmitTime(DatetimeUtils.getTimestamp(rec.submitTime()))
                    .build();
            responseObserver.onNext(vo);
        });
        responseObserver.onCompleted();
    }
}
