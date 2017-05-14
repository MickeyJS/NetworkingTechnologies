package sieci;

import sieci.zadanie1.Exercise1;

//NIE DZIAŁAJĄCY MAIN [Pomocniczy]
public class Main {
//Main do zadania 1
    public static void main(String[] args) {

        if (args.length < 1 || args.length > 2) {
            printUsage();
            System.exit(1);
        }

        String zadanie = args[0];

        switch (zadanie) {

            case "zadanie1":
                new Exercise1().start();
                break;



            default:
                printUsage();
                System.exit(1);
                break;
        }


    }

    private static void printUsage() {
        System.out.println("USAGE: <zadanie1/zadanie2> <(gdy zadanie2) ścieżka do folderu z grafem>");

    }

}
