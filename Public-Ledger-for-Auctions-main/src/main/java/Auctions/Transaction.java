package Auctions;

import java.io.Serializable;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.bouncycastle.util.encoders.Hex;

public class Transaction implements Serializable {

    PublicKey publicKeySender;
    PublicKey keyReceiver;
    int coin;
    final long timeStamp;
    public byte signature[];

    public Transaction(PublicKey PublicKeySender, PrivateKey PrivateKeySender, PublicKey keyReceiver, int coin){
        this.publicKeySender = PublicKeySender;
        this.keyReceiver = keyReceiver;
        this.coin  = coin;
        this.timeStamp = System.currentTimeMillis(); //fazer getTime() para aqui
        generateSignature(PrivateKeySender);
    }

    public void generateSignature(PrivateKey PrivateKeySender){
        String data = getStringFromKey(publicKeySender);
        this.signature = applyECDSASig(PrivateKeySender, data);
    }

    private String getStringFromKey(Key key) {

        return Hex.toHexString(Hex.encode(key.getEncoded()));
    }

    public static byte[] applyECDSASig(PrivateKey privateKey, String data){
        Signature dsa;
        try {
            dsa = Signature.getInstance("ECDSA", "BC"); //Especifica que o algoritmo de assinatura é o "ECDSA" encontrado no BouncyCastle
            dsa.initSign(privateKey);// Inicializa o objeto para assinatura
            dsa.update(data.getBytes()); //Atualiza os dados
            return dsa.sign(); //Retorna os bytes de assinatura dos dados atualizados
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(){
        return "["+ this.coin +" , " + this.timeStamp +" , " +this.signature +"]";
    }

    public void printTransaction() {
        System.out.println("Sender: "+publicKeySender+" Receiver: "+keyReceiver+" -> Coins: "+coin);
    }

    //public String toString(){
    //    return "[" + this.PublicKeySender + " , " + this.keyReceiver+ " , " + this.coin +" , " + this.timeStamp +" , " +this.signature +"]";
    //}

}
