import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

class Labyrint {
    private int _kolonner;
    private int _rader;
    private Rute[][] _ruter;

    private Labyrint(int kolonner, int rader, Rute[][] ruter) {
        _kolonner = kolonner;
        _rader = rader;
        _ruter = ruter;
    }

    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
            Scanner in = new Scanner(fil);
            String[] info = in.nextLine().split(" ");
            int rader = Integer.parseInt(info[0]);
            int kolonner = Integer.parseInt(info[1]);
            Labyrint labyrint = new Labyrint(kolonner, rader, new Rute[kolonner][rader]);


            for (int i = 0; i < rader; i++) {
                String[] line = in.nextLine().split("");
                for (int j = 0; j < kolonner; j++) {
                    if (line[j].equals("#")) {
                        labyrint.hentRuter()[j][i] = new SvartRute(j, i, labyrint);
                        //labyrint.setRute(x, y, nyVerdi);
                    }
                    else {
                        if (labyrint.erKant(j, i)) {
                            labyrint.hentRuter()[j][i] = new Aapning(j, i, labyrint);
                        } else {
                            labyrint.hentRuter()[j][i] = new HvitRute(j, i, labyrint);
                        }
                    }
                }
            }
            for (Rute[] ruter : labyrint.hentRuter()) {
                for (Rute rute : ruter) {
                    rute.finnAapneNaboer();
                }
            }
            in.close();
            return labyrint;
    }

    private boolean erKant(int kolonne, int rad) {
        return kolonne + 1 >= this._kolonner || kolonne - 1 < 0 || rad + 1 >= this._rader || rad - 1 < 0;
    }

    public Liste<String> finnAlleUtveierFra(int kolonne, int rad) {
        Rute rute = hentRuter()[kolonne][rad];
        rute.finnUtvei();     
        Liste<String> utveier = rute.hentUtveier();
        tilbakestillRuter();
        return utveier;
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

    private void tilbakestillRuter() {
        for (Rute[] ruter : this.hentRuter()) {
            for (Rute r : ruter) {
                r.settBlank();
            }
        }
    }
}