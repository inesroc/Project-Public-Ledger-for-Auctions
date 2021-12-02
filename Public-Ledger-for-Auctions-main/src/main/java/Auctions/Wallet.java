package Auctions;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

    public PublicKey publicKey;
    public PrivateKey privateKey;
    public int lastBlockCheck;
    public int balance;
    public ArrayList<Transaction> trans;
    Miner miner;

    public int getLastBlockCheck() {
        return lastBlockCheck;
    }

    public Wallet(BlockChain bChain){
        generateKeyPair();
        this.balance = 0;
        this.lastBlockCheck = -1;
        this.trans = null;
        miner = new Miner(bChain, this.publicKey, this.privateKey);

    }

    public void generateKeyPair(){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");//Especifica o algoritmo de geração das chaves
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); //Para produzir uma sequencia pseudo-aleatória de uma "seed" para ser usada na geração das chaves
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");//especifica a Curva Elíptica
            // Inicializa o gerador de chaves
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            // Cria o par de chaves e guarda as nas variáveis
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getBalance(BlockChain blockChain){
        int size = blockChain.getSizeChain();
        for (int i=getLastBlockCheck()+1; i<size; i++){
            Block block = blockChain.getBlock(i);
            for(Transaction t : block.transaction){
                if(t.publicKeySender == this.publicKey){
                    this.balance -= t.coin;
                    trans.add(t);}
                else if (t.keyReceiver == this.publicKey){
                    this.balance += t.coin;
                    trans.add(t);
                }
            }
        }
        this.lastBlockCheck = size-1;
        return this.balance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void startMining(int threads){
        miner.startMining(threads);
    }

    public void printBalance(BlockChain blockChain) {
        System.out.println("Your balance is "+getBalance(blockChain));
    }

    public void printTransaction(BlockChain blockChain) {
        printBalance(blockChain);
        if (trans == null){
            System.out.println("You don't have any transactions\n");
            return;
        }
        for(Transaction t : trans){
            t.printTransaction();


        }
    }
}
