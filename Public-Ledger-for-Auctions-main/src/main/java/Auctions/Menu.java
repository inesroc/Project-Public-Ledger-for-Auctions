package Auctions;

import java.security.Security;
import java.util.Scanner;

//interface basica
public class Menu {

    public static int callMenu(){

        System.out.println("-------------------Menu--------------------");
        System.out.println("1-> Creating Wallets\n2-> Creating Transactions\n3-> Do the mining\n"); //colocar aqui as varias opcoes

        Scanner in = new Scanner(System.in);
        int ch = in.nextInt();

        return ch;

    }

    /*
    Cada uma das opcoes vai para uma parte do test (criar wallets, criar trasactions, etc...).
    Com -1 acaba o programa.
    Qualquer outro valor e ignorado.
    */
    public static void main(String args[]) {

        Teste t = new Teste();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // adiciona um "provider" para a proxima localização possível

        if(t.superUSer == null){
            t.createSuperUser();
        }

        int choice = callMenu();

        while (choice != -1) {
            switch (choice) { //colocar aqui as varias opcoes
                case (1):
                    t.createWallets();
                    break;

                case (2):
                    t.createBlockChain();
                    break;

                case(3):
                    t.doMining();
                    break;

                default:
                    break;
            }

            choice = callMenu();
        }
    }
}
