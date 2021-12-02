package Auctions;

import org.bouncycastle.util.encoders.Hex;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.security.NoSuchAlgorithmException;

public class Block implements Serializable{

    public String previousHash;
    ArrayList<Transaction> transaction;
    public AtomicLong nounce;
    public String selfHash;
    private int diffChain = 5;
    private long id;
    long timeStamp;
    final static int NUMBER_TRANSACTIONS = 4;

    public Block(long id, String previousHash, ArrayList<Transaction> transactions){ //a aceitar só uma transação por bloco (mudar depois)

        if(SizeListTransactionsOkay(transactions)) {

            this.id = id;
            this.previousHash = previousHash;
            this.transaction = transactions;
            this.timeStamp = System.currentTimeMillis();
            this.nounce = new AtomicLong();
            this.selfHash = calculateHash(nounce);
        }
    }


    public boolean SizeListTransactionsOkay(ArrayList<Transaction> transactions){
        int size = transactions.size();
        if(size != NUMBER_TRANSACTIONS){
            System.out.println("Block needs to have 4 transactions");
            return false;
        }
        return true;


    }

    final public String calculateHash(Object nounce){

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha256");
            byte[] hash = messageDigest.digest(getData(nounce).getBytes());//transforma a mensagem numa hash em bytes
            return Hex.toHexString(hash); // passa a hash de bytes para hexadecimal
        } catch (NoSuchAlgorithmException e) {//caso existe não reconheça o algoritmo de hash especificado
            throw new RuntimeException(e);
        }
    }

    //ver mais tarde
    public String getData(Object nounce){
        return previousHash + " | " + Long.toString(timeStamp) + " | " + nounce.toString();// + " | " + transaction.coin;
    }


    public synchronized static boolean validateHash(int difficulty, String hash){
        String target = new String(new char[difficulty]).replace('\0', '0');
        return hash.substring(0, difficulty).equals(target);
    }

    public boolean isMined(String hash){
        return validateHash(this.diffChain, hash);
    }

    public long getID(){
        return id;
    }

    public String getPreviousHash(){
        return previousHash;
    }

    public ArrayList<Transaction> getTransaction() {
        return transaction;
    }

    public int getDiffChain(){
        return diffChain;
    }

    public void setDiffChain(int diffChain){
        this.diffChain = diffChain;
    }

    public Long getTimeStamp(){
        return timeStamp;
    }

    public AtomicLong getNounce(){
        return nounce;
    }

    public String getSelfHash(){
        return selfHash;
    }

    public static int getNumberTransactions(){
        return NUMBER_TRANSACTIONS;
    }

}
