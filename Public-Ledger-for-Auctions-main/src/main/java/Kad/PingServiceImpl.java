package Kad;


import io.grpc.stub.StreamObserver;

public class PingServiceImpl extends PingServiceGrpc.PingServiceImplBase{

    @Override
    public void ping(Target request, StreamObserver<NodeInfo> responseObserver){
        System.out.println(request);

        System.out.println("Hello there, " + request.getSender().getId());

        NodeInfo response = NodeInfo.newBuilder()
                .setId(request.getSender().getId())
                .setIp(request.getSender().getIp())
                .setPort(request.getSender().getPort())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
