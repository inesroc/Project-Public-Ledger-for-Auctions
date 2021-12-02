package Auctions;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;


public class Miner extends Thread{

    BlockChain blockChain;
    Boolean isMining;
    PublicKey publicKey;
    PrivateKey privateKey;
    int reward = 2;


    public Miner(BlockChain b, PublicKey pubK, PrivateKey priK){
        this.blockChain = b;
        this.isMining = false;
        this.publicKey = pubK;
        this.privateKey = priK;
    }

    class MinerThread extends Thread {
        ArrayList<Transaction> transactions;
        int id;

        public MinerThread(ArrayList<Transaction> trans, int idx){
            this.transactions = trans;
            this.id = idx;
        }

        @Override
        public void run() {

               // Queue<Transaction> all_transactions = blockChain.getTransactions();

                Block block = new Block(blockChain.getIdLastBlock(), blockChain.getHashLastBlock(), transactions);
                System.out.println("MINER (INFO THREAD n "+this.id+") - first hash " + block.getSelfHash());

                long myNounce;
                String tempHash = "";

                while (isMining) {
                    //System.out.println("MINER (INFO) ("+this.id+") - mining... ");
                    myNounce = Math.abs(ThreadLocalRandom.current().nextLong());
                    tempHash = block.calculateHash(myNounce);

                    if (block.isMined(tempHash)) {
                        stopMining();
                        block.nounce.set(myNounce);
                        block.selfHash = tempHash;
                        System.out.println("MINER (INFO THREAD n "+this.id+") - found hash " + tempHash);
                        blockChain.addBlock(block);
                        blockChain.createTransaction(publicKey, privateKey, publicKey, reward);
                        blockChain.removeTransactions();
                        break;
                    }
                }

            System.out.println("MINER (INFO THREAD n "+this.id+") - STOP MINING " + tempHash);
        }


    }

    //procurar todas  as transações que involvam o user com esta public igual às desta transação na blockchain
    public boolean isTransactionValid(Transaction trans){

        int count = 0;

        for(Block block : blockChain.chain) { //ver a parte da blockchain estar a private
            for(Transaction t : block.transaction){

                if(trans.publicKeySender == trans.keyReceiver)
                    count += t.coin;
                else {
                    if (trans.publicKeySender == t.publicKeySender)
                        count -= t.coin;

                    if (trans.keyReceiver == t.keyReceiver)
                        count += t.coin;
                }
            }
        }
        System.out.println("count: "+count+" --- trans coin: "+trans.coin);

        if(count < trans.coin)
            return false;

        else
            return true;
    }


    public void startMining(int threads) {
        isMining = true;
        System.out.println("MINER (USER) - Starting mining");
        Queue<Transaction> all_transactions = blockChain.getTransactions();
//        for(Transaction t : all_transactions)
//            System.out.println("BEFORE " + t );

        int number_transactions = 0;

        ArrayList<Transaction> checkTrans = new ArrayList<>();
        while (number_transactions < 4) {
            if(all_transactions.peek()!= null){
                Transaction trans = all_transactions.remove();
                if (isTransactionValid(trans)) {
                    checkTrans.add(trans);
                    number_transactions = number_transactions + 1;
                }
            }
            else{
                System.out.println("MINER (INFO) - There are not enough transactions to mine");
                return;

            }
        }

        for(int i=0; i<threads; i++) {
            MinerThread x = new MinerThread(checkTrans, i);
            x.start();
        }

    }

    public void stopMining(){
        isMining = false;
        System.out.println("MINER (USER) - Stop mining");
    }

}

