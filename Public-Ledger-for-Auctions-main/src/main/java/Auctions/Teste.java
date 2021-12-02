package Auctions;

import java.security.Security;
import java.util.concurrent.TimeUnit;

public class Teste {

    static Wallet superUSer;
    static Wallet wallet1;
    static Wallet wallet2;
    static Wallet wallet3;
    static Wallet wallet4;
    static BlockChain bChain;

    public Teste() {
        this.superUSer = null;
        this.wallet1 = null;
        this.wallet2 = null;
        this.wallet3 = null;
        this.wallet4 = null;
        this.bChain = null;
    }

    public void createSuperUser(){

        superUSer = new Wallet(null);
        bChain = new BlockChain(superUSer);

        while(bChain.genesis)
            continue;
        System.out.println("Finish Genesis");

        System.out.println("SuperUser Created!");
    }

    public void createWallets(){

        wallet1 = new Wallet(bChain);
        wallet2 = new Wallet(bChain);
        wallet3 = new Wallet(bChain);
        wallet4 = new Wallet(bChain);

        System.out.println("Wallets Created!");
    }

    public void createBlockChain(){

        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet1.getPublicKey(), 2);
        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet2.getPublicKey(), 4);
        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet3.getPublicKey(), 6);
        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet4.getPublicKey(), 8);

        System.out.println("BlockChain Created!");
    }

    public void doMining(){

        wallet2.startMining(5);

        System.out.println("Mining Done!");
    }

/*
    public static void main(String args[]) throws Exception{

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // adiciona um "provider" para a proxima localização possível
        superUSer = new Wallet();
        bChain = new BlockChain(superUSer);

        while(bChain.genesis)
            continue;
        System.out.println("Finish Genesis");

        wallet1 = new Wallet();
        wallet2 = new Wallet();
        wallet3 = new Wallet();
        wallet4 = new Wallet();

        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet1.getPublicKey(), 2);
        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet2.getPublicKey(), 4);
        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet3.getPublicKey(), 6);
        bChain.createTransaction(superUSer.getPublicKey(), superUSer.getPrivateKey(), wallet4.getPublicKey(), 8);

        Miner miner = new Miner(bChain, wallet1.getPublicKey(), wallet1.getPrivateKey());
        miner.startMining(5);
*/
//        bChain = new BlockChain();
//        bChain.createTransaction(wallet1.getPublicKey(), wallet1.getPrivateKey(), wallet1.getPublicKey(), 1);
//        bChain.createTransaction(wallet1.getPublicKey(), wallet1.getPrivateKey(), wallet2.getPublicKey(), 3);
//        bChain.createTransaction(wallet2.getPublicKey(), wallet2.getPrivateKey(), wallet1.getPublicKey(), 4);
//        bChain.createTransaction(wallet2.getPublicKey(), wallet2.getPrivateKey(), wallet1.getPublicKey(), 6);
//        bChain.createTransaction(wallet1.getPublicKey(), wallet1.getPrivateKey(), wallet2.getPublicKey(), 5);
//        bChain.createTransaction(wallet1.getPublicKey(), wallet1.getPrivateKey(), wallet2.getPublicKey(), 7);
//        bChain.createTransaction(wallet2.getPublicKey(), wallet2.getPrivateKey(), wallet1.getPublicKey(), 2);
//        bChain.createTransaction(wallet2.getPublicKey(), wallet2.getPrivateKey(), wallet1.getPublicKey(), 8);
//
//        Miner miner = new Miner(bChain, wallet1.getPublicKey(), wallet1.getPrivateKey());
//        miner.startMining(5);
//        //wallet2 = new Wallet(1);



        //miner.run();
        //TimeUnit.SECONDS.sleep(10);
        //System.out.println("hiii");
        //miner.stopMining();

        //        Transaction trans1 = new Transaction(wallet1, wallet2, 5);
//        bChain.add(bChain.createBlock(trans1));
//
//        Transaction trans2 = new Transaction(wallet1, wallet2, 19);
//        bChain.add(bChain.createBlock(trans2));
//
//        Transaction trans3 = new Transaction(wallet2, wallet1, 20);
//        bChain.add(bChain.createBlock(trans3));
//
////        Transaction trans4 = new Transaction(user1, user2, 20);
////        Transaction trans5 = new Transaction(user2, user1, 3);
//
//
//
//
////        bChain.add(bChain.createBlock(trans4));
////        bChain.add(bChain.createBlock(trans5));
//
//        System.out.println("\nChain size : " + bChain.getChainList().size());
//
//        for (int i = 0; i < bChain.getChainList().size(); i++) {
//
//            Block thisBlock = bChain.getBlock(i);
//            System.out.println("\nBlock ID : " + thisBlock.getID());
//            System.out.println("Utilizador criador : " + thisBlock.transaction.getUserSend());
//            System.out.println("PreviousHash : " + thisBlock.previousHash);
//            System.out.println("Hash : " + thisBlock.selfHash);
//            System.out.println("Nounce : " + thisBlock.nounce);
//        }
//    }
}