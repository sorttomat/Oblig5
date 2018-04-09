import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

class Labyrint {
    private static int _kolonner;
    private static int _rader;
    private static Rute[][] _ruter;
    private Liste<String> utveier;

    private Labyrint() {
    }

    public static Labyrint lesFraFil(File fil) {
        //Les inn filen, opprett ruter, finn alle naboer, sjekk om ruten er en åpning,  sett inn i _ruter, returner labyrint.
        //Skal ikke håndtere FileNotFoundException, skal kaste den videre til metoden som kalte denne metoden.
        try {
            Scanner in = new Scanner(fil);
            String[] info = in.nextLine().split(" ");
            int rader = Integer.parseInt(info[0]);
            int kolonner = Integer.parseInt(info[1]);
            Labyrint labyrint = new Labyrint();
            _kolonner = kolonner;
            _rader = rader;
            _ruter = new Rute[kolonner][rader];

            for (int i = 0; i < rader; i++) {
                String[] line = in.nextLine().split("");
                for (int j = 0; j < kolonner; j++) {
                    if (line[j].equals("#")) {
                            _ruter[j][i] = new SvartRute(j, i, labyrint);
                        }
                     else {
                        if (erKant(j, i)) {
                            _ruter[j][i] = new Aapning(j, i, labyrint);
                        } else {
                            _ruter[j][i] = new HvitRute(j, i, labyrint);
                        }
                    }
                }
            }
            for (Rute[] ruter : _ruter) {
                for (Rute rute : ruter) {
                    rute.finnAapneNaboer();
                }
            }
            return labyrint;

        } catch (FileNotFoundException ex) { //Skal kastes til metoden som kaller lesFraFil(). Vet ikke helt hvordan
            return null;
        }
    }

    private static boolean erKant(int kolonne, int rad) {
        if (kolonne + 1 >= _kolonner || kolonne - 1 < 0 || rad + 1 >= _rader || rad - 1 < 0) {
            return true;
        }
        return false;
    }

    public static  Liste<String> finnUtveiFra(int kolonne, int rad) {
        Rute rute = _ruter[kolonne][rad];
        rute.finnUtvei();
        return rute.hentUtveier();
    }

    public Rute[][] hentRuter() {
        return _ruter;
    }

    public int hentAntallKolonner() {
        return _kolonner;
    }

    public int hentAntallRader() {
        return _rader;
    }

}