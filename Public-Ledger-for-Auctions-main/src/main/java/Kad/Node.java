package Kad;

import Auctions.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Integer.parseInt;


public class Node {

    public String ID;
    public byte[] HashedID;
    public String IP;
    public String Port;
    public int FailedConns;
    public StreamObserver[] ConnList ;
    private static Set<StreamObserver<User>> observers = ConcurrentHashMap.newKeySet();
    final static int MAX_TRY_CONN_COUNT = 3;

    public Node(String ip, String port){
        this.ID = String.valueOf(Math.random()).substring(2);
        this.HashedID = ConvertPeerID(this.ID);
        this.IP = ip;
        this.Port = port;
        this.FailedConns = 0;

        try{
            myServer();
        }catch (IOException ioEx){
            System.out.println("Server start not possible.\n");
            ioEx.printStackTrace();
        }catch (InterruptedException iEx){
            System.out.println("Server process interrupted\n");
            iEx.printStackTrace();
        }
    }

     public void myServer() throws IOException, InterruptedException {
         Server server = ServerBuilder.forPort(parseInt(this.Port))
                 .addService(new PingServiceImpl()).build();

         server.start();
     }

    public void ping(String ip, String port){
        int portInt = parseInt(port);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, portInt).usePlaintext().build();
        Kad.PingServiceGrpc.PingServiceBlockingStub stub =
                Kad.PingServiceGrpc.newBlockingStub(channel);


        Kad.NodeInfo thisNode = Kad.NodeInfo.newBuilder()
                .setIp(this.IP)
                .setPort(this.Port)
                .build();

        Kad.NodeInfo nodeInfo = stub.ping(
                        Target.newBuilder()
                        .setTargetId(ip)
                        .setSender(thisNode)
                        .build());
        channel.shutdown();
    }

    // ConvertPeerID creates a DHT ID by hashing a Peer ID
    final public byte[] ConvertPeerID(String id){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha256");
            byte[] hash = messageDigest.digest(id.getBytes());//transforma a mensagem numa hash em bytes
            return hash;
        } catch (NoSuchAlgorithmException e) {//caso existe não reconheça o algoritmo de hash especificado
            throw new RuntimeException(e);
        }
    }
}
