package Auctions;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BlockChain implements Serializable {

    ArrayList<Block> chain;
    Queue<Transaction> transactionPool;
    int numBlockGenesis = 4;
    boolean genesis = true;

    public BlockChain(Wallet superUser){
        this.chain = new ArrayList<>();
        this.transactionPool = new LinkedList<Transaction>();
        createBlockGenesis(superUser);
    }

    private void createBlockGenesis(Wallet superUser) {
        int NUMBER_TRANSACTIONS = Block.getNumberTransactions();
        MineGenesis miner = new MineGenesis(this, superUser.getPublicKey(), superUser.getPrivateKey());
        for(int i =0; i<numBlockGenesis; i++){
            for(int j=0; j<NUMBER_TRANSACTIONS; j++){
                createTransaction(superUser.getPublicKey(), superUser.getPrivateKey(), superUser.getPublicKey(), 10);
            }

        }
        miner.startMining(5);
        genesis = false;
    }

    public void createTransaction(PublicKey PublicKeySender, PrivateKey PrivateKeySender, PublicKey keyReceiver, int coins) {
        try{
            Transaction trans = new Transaction(PublicKeySender,PrivateKeySender , keyReceiver, coins);
            transactionPool.add(trans);
            System.out.println("INFO - Transaction added to the pool");
        }
        catch (Exception e){
            System.out.println("INFO (ERROR) - Transaction was not added to the pool");
        }

    }

    public void addBlock(Block block){
        chain.add(block);
        System.out.println("INFO - Block added to the Block Chain");
    }

    public Queue<Transaction> getTransactions(){

        return this.transactionPool;
    }

    public String getHashLastBlock(){
        int size = chain.size();
        if (size == 0){
            return "";
        }
        Block block = chain.get(size - 1);
        return block.getSelfHash();
    }

    public long getIdLastBlock(){
        int size = chain.size();
        if (size == 0){
            return 0;
        }
        Block block = chain.get(size - 1);
        return block.getID();

    }

    public void removeTransactions() {
        for(int i=0; i<4; i++){
            transactionPool.remove();
        }
    }


    public int getSizeChain(){
        return chain.size();
    }

    public Block getBlock(int pos){
        return chain.get(pos);
    }
}