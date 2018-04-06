import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
//Labyrint skal være private!!! Nå tester jeg bare om alt fungerer på den måten jeg vet hvordan.


class Labyrint {
    //Rute[kolonne][rad] viktig!!!!!!!! Ikke rute[rad][kolonne]. 
    //Pass på når labyrinten blir lest inn.

    private int _kolonner;
    private int _rader;
    private static Rute[][] _ruter;
    private Liste<String> utveier;

    public Labyrint(File fil){//Rute[][] ruter, int antKolonner, int antRader) {
        lesFraFil(fil);
    }

    public Labyrint lesFraFil(File fil) { //Skal egentlig være statisk
        //Les inn filen, opprett ruter, finn alle naboer, sjekk om ruten er en åpning,  sett inn i _ruter, returner labyrint.
        //Skal ikke håndtere FileNotFoundException, skal kaste den videre til metoden som kalte denne metoden.
        try {
        Scanner in = new Scanner(fil);
        String[] info = in.nextLine().split(" ");
        int rader = Integer.parseInt(info[0]);
        int kolonner = Integer.parseInt(info[1]);
        _kolonner = kolonner;
        _rader = rader;
        _ruter = new Rute[kolonner][rader];

        for (int i = 0; i < rader; i++) {
            String[] line = in.nextLine().split("");
            for (int j = 0; j < kolonner; j++) {
                System.out.print(line[j]);
                if (line[j].equals("#")) {
                    //_ruter[j][i] = new SvartRute(j, i, this); //Skal få med referanse til dette labyrint-objektet ...
                }
            }
            System.out.println();
        }

        return null;
    } catch (FileNotFoundException ex) {
        return null;
    }
    }

    private boolean erAapning(Rute rute) {
        //Hvis rute = åpen, og kolonne + 1 >= antall kolonner eller kolonne - 1 < 0 eller rad + 1 >= antall rader eller rad - 1 < 0, så er ruten en åpning.
        return true;
    }

    private Liste<String> finnUtveiFra(int kolonne, int rad) {
        //Finn rute-objektet med de koordinatene
        //Kall finnUtvei() på den ruten (som igjen kaller gaa() på sine naboruter osv.)

        //Returner ruten.hentUtveier() (Liste på formen "(kol, rad) --> (kol, rad)", "(kol, rad) --> (kol, rad)")
        return null;
    }






}