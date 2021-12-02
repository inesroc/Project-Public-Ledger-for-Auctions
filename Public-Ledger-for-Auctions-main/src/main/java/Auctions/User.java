package Auctions;

import Kad.Node;
import Kad.Util;

import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.security.Security;
import java.util.Scanner;

public class User {
    static BlockChain bChain;
    static Wallet superUSer;
    static Wallet wallet = null;

    public static int callMenu(){

        System.out.println("-------------------Menu--------------------");
        if(wallet == null)
            System.out.println("1-> Create the user");
        else
            System.out.println("2-> Check Wallet\n3-> Make a transaction\n4-> Create an Auction\n5-> Check an auction\n6-> Mine\n7-> Quit"); //colocar aqui as varias opcoes

        Scanner in = new Scanner(System.in);
        int ch = in.nextInt();

        return ch;

    }

    private static int callMenuWallet(){
        System.out.println("-----------------Menu Wallet------------------");
        System.out.println("1-> Check Balance\n2-> Check Transactions\n3-> Return\n");

        Scanner in = new Scanner(System.in);
        int ch = in.nextInt();

        return ch;
    }



    public static void main(String args[]) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // adiciona um "provider" para a proxima localização possível
        int choice = callMenu();
        String machineIP = "localhost";

        try {
            machineIP = Util.getMachineIP();
        }catch (IOException ex){
            System.out.println("GET request didn't work.\n");
            ex.printStackTrace();
        }

        //verificar se o próprio servidor está ativo
        Node thisNode = new Node(machineIP,"8080");
        thisNode.ping("localhost","8080");

        while (choice != -1) {
            switch (choice) { //colocar aqui as varias opcoes
                case(1):
                    createWallet();
                    break;

                case (2):
                    checkWallet();
                    break;

                case (3):
                    makeTransaction();
                    break;

                case(4):
                    createAuction();
                    break;

                case(5):
                    checkAuction();
                    break;
                case(6):
                    mine();
                    break;
                case(7):
                    return;

                default:
                    break;
            }

            choice = callMenu();
        }

    }



    private static void createWallet(){
        // TODO send real blockchain and remove this
        superUSer = new Wallet(null);
        bChain = new BlockChain(superUSer);
        while(bChain.genesis)
            continue;


        wallet = new Wallet(bChain);
        System.out.println("Wallet created!\n");
    }

    private static void checkWallet() {
        int choice = callMenuWallet();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // adiciona um "provider" para a proxima localização possível

        while (choice != -1) {
            switch (choice) { //colocar aqui as varias opcoes
                case(1):
                    wallet.printBalance(bChain);
                    break;

                case (2):
                    wallet.printTransaction(bChain);
                    break;
                default:
                    return;
            }

            choice = callMenuWallet();
        }
    }

    private static void makeTransaction() {

        // TODO Mudar isto para ler uma public key
        System.out.println("What is the public key of recipient?\n");
        PublicKey keyReceiver = null;


        System.out.println("How many coins do you want to transfer\n");
        Scanner in = new Scanner(System.in);
        int coins = in.nextInt();
        bChain.createTransaction(wallet.publicKey, wallet.getPrivateKey(), keyReceiver, coins);

    }

    private static void createAuction() {
    }

    private static void checkAuction() {
    }

    private static void mine() {
        System.out.println("How many threads?\n");
        Scanner in = new Scanner(System.in);
        int threads = in.nextInt();
        wallet.startMining(threads);
    }

}
