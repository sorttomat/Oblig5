import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

class LabyrintGUI extends Application {
    String filnavn = "some string";
    Labyrint labyrint = fraFil(filnavn);
    Text statusinfo;

    private Labyrint fraFil(String filnavn) {
        try {
            File fil = new File(filnavn);
            Labyrint labyrint = Labyrint.lesFraFil(fil); 
            return labyrint;     
        } catch (FileNotFoundException ex) {
            return null;
        } 
    }

    class Trykkbehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            trykkPaaRute((Rute) e.getSource());
        }
    }

    private void trykkPaaRute(Rute rute) {
        // Når en tast blir trykket på (forutsatt at det er en hvit tast), skal alle
        // utveier finnes og returneres hit.
        if (rute.charTilTegn() != '.') {
            statusinfo.setText("Dette er en lukket rute, vennligst velg en aapen.");
            return;
        }
        statusinfo.setText("Velg en rute.");
        Liste<String> utveier = labyrint.finnAlleUtveierFra(rute.hentKolonne(), rute.hentRad());
        Lenkeliste<boolean[][]> listeMedUtveierBoolean = new Lenkeliste<boolean[][]>();

        for(String utvei : utveier) {
            boolean[][] løsning = new boolean[labyrint.hentAntallRader()][labyrint.hentAntallKolonner()];
            listeMedUtveierBoolean.leggTil(løsning);
        }

        // Deretter skal et nytt vindu dukke opp der alle løsningene vises.
        // Når bruker lukker dette vinduet, er det første der fremdeles og bruker kan
        // trykke på en ny tast eller avslutte.
    }

    /**
     * Konverterer losning-String fra oblig 5 til en boolean[][]-representasjon av
     * losningstien.
     * 
     * @param losningString String-representasjon av utveien
     * @param bredde        bredde til labyrinten
     * @param hoyde         hoyde til labyrinten
     * @return 2D-representasjon av rutene der true indikerer at ruten er en del av
     *         utveien.
     */
    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s", ""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        return losning;
    }

    @Override
    public void start(Stage scene) {

    }
}