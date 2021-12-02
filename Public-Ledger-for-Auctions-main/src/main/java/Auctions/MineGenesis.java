package Auctions;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class MineGenesis {


    BlockChain blockChain;
    Boolean isMining;
    PublicKey publicKey;
    PrivateKey privateKey;
    int reward = 2;


    public MineGenesis(BlockChain b, PublicKey pubK, PrivateKey priK){
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
            System.out.println("    MINER (INFO THREAD n "+this.id+") - first hash " + block.getSelfHash());

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
                    System.out.println("    MINER (INFO THREAD n "+this.id+") - found hash " + tempHash);
                    blockChain.addBlock(block);
                    blockChain.createTransaction(publicKey, privateKey, publicKey, reward);

                    break;
                }
            }
//            }
//            catch (Exception e) {
//                // Throwing an exception
//                System.out.println("MINER (ERROR) ("+this.id+") - Exception is caught");
//            }
        }


    }

    public void startMining(int threads) {

        System.out.println("MINER GENESIS (INFO) - Starting mining");
        Queue<Transaction> all_transactions = blockChain.getTransactions();

        int numTran = Block.getNumberTransactions();
        MinerThread x = null;
        int size = all_transactions.size();
        for(int i=0; i<size; i+=4){
            int number_transactions = 0;
            ArrayList<Transaction> checkTrans = new ArrayList<>();
            while (number_transactions < numTran) {
                Transaction trans = all_transactions.remove();
                checkTrans.add(trans);
                number_transactions = number_transactions + 1;
            }
            // Wait for threads to stop
            try {
                if (i!= 0)
                    x.join();
            }
            catch (Exception e) {
                System.out.println(e);
            }

            x = new MineGenesis.MinerThread(checkTrans, i);
            isMining = true;
            x.start();

        }
        try {
            x.join();
            System.out.println("MINER GENESIS (INFO) - Stop GENESIS mining");
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    public void stopMining(){
        isMining = false;
        System.out.println("MINER GENESIS (INFO) - Stop mining");
    }

//    public void startMining(int threads) {
//        isMining = true;
//        System.out.println("MINER GENESIS (INFO) - Starting mining");
//        Queue<Transaction> all_transactions = blockChain.getTransactions();
////        for(Transaction t : all_transactions)
////            System.out.println("BEFORE " + t );
//
//        int number_transactions = 0;
//
//        ArrayList<Transaction> checkTrans = new ArrayList<>();
//        while (number_transactions < 4) {
//            Transaction trans = all_transactions.remove();
//            checkTrans.add(trans);
//            number_transactions = number_transactions + 1;
//        }
//
//
//        for(int i=0; i<threads; i++) {
//            MineGenesis.MinerThread x = new MineGenesis.MinerThread(checkTrans, i);
//            x.start();
//        }
//    }


}
