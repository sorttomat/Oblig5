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

public class LabyrintGUI extends Application {
    String filnavn = "labyrint3.txt";
    Labyrint labyrint;
    Text statusinfo;
    GridPane rutenett;
    Rute[][] brett;
    int losningTeller;
    Lenkeliste<boolean[][]> listeMedUtveierBoolean;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage teater) {
        labyrint = fraFil(filnavn);
        statusinfo = new Text("Velg en rute");
        statusinfo.setFont(new Font(20));
        statusinfo.setX(10);
        statusinfo.setY(410);

        Button stoppknapp = new Button("Stopp");
        stoppknapp.setLayoutX(10);
        stoppknapp.setLayoutY(410);
        StoppBehandler stopp = new StoppBehandler();
        stoppknapp.setOnAction(stopp);

        Button forrigeKnapp = new Button("Forrige løsning");
        forrigeKnapp.setLayoutX(10);
        forrigeKnapp.setLayoutY(440);
        TrykkForrigeBehandler forrige = new TrykkForrigeBehandler();
        forrigeKnapp.setOnAction(forrige);

        Button nesteKnapp = new Button("Neste løsning");
        nesteKnapp.setLayoutX(150);
        nesteKnapp.setLayoutY(440);
        TrykkNesteBehandler neste = new TrykkNesteBehandler();
        nesteKnapp.setOnAction(neste);

        brett = labyrint.hentRuter();
        TrykkRuteBehandler trykkRute = new TrykkRuteBehandler();
        for (Rute[] rad : brett) {
            for (Rute rute : rad) {
                if (rute.charTilTegn() == '.') {
                    rute.setStyle("-fx-base: #ffffff;");
                }
                else {
                    rute.setStyle("-fx-base: #000000;");
                }
                rute.setOnAction(trykkRute);
            }
        }

        rutenett = new GridPane();
        for (int i = 0; i < labyrint.hentAntallRader(); i++) {
            for (int j = 0; j < labyrint.hentAntallKolonner(); j++) {
                rutenett.add(brett[j][i], j, i);
            }
        }
        rutenett.setLayoutX(10);
        rutenett.setLayoutY(10);

        Pane kulisser = new Pane();
        kulisser.setPrefSize(400, 500);
        kulisser.getChildren().add(rutenett);
        kulisser.getChildren().add(statusinfo);
        kulisser.getChildren().add(stoppknapp);
        kulisser.getChildren().add(nesteKnapp);
        kulisser.getChildren().add(forrigeKnapp);

        Scene scene = new Scene(kulisser);

        teater.setTitle("Spill");
        teater.setScene(scene);
        teater.show();
    }

    private void trykkPaaRute(Rute rute) {
        losningTeller = 0;
        nullstillBrett();
        if (rute.charTilTegn() != '.') {
            statusinfo.setText("Dette er en lukket rute, vennligst velg en aapen.");
            return;
        }
        Liste<String> utveier = labyrint.finnAlleUtveierFra(rute.hentKolonne(), rute.hentRad());
        listeMedUtveierBoolean = new Lenkeliste<boolean[][]>();
        if (utveier.stoerrelse() == 0) {
            statusinfo.setText("Ingen utveier");
            return;
        }
        for(String utvei : utveier) {
            boolean[][] løsning = losningStringTilTabell(utvei, labyrint.hentAntallKolonner(), labyrint.hentAntallRader());
            listeMedUtveierBoolean.leggTil(løsning);
        }
        printLosningNr();
        fargeleggUtvei(listeMedUtveierBoolean.hent(0));
    }

    private void trykkNeste() {
        if (losningTeller < listeMedUtveierBoolean.stoerrelse()-1) {
            nullstillBrett();
            losningTeller++;
            printLosningNr();
            fargeleggUtvei(listeMedUtveierBoolean.hent(losningTeller));
        }
        else {
            statusinfo.setText("Ingen flere løsninger");
        }
    }

    private void trykkForrige() {
        if (losningTeller > 0) {
            nullstillBrett();
            losningTeller--;
            printLosningNr();
            fargeleggUtvei(listeMedUtveierBoolean.hent(losningTeller));
        }
        else {
            statusinfo.setText("Ingen flere løsninger");
        }
    }

    private void fargeleggUtvei(boolean[][] løsning) {
        for (int i = 0; i < labyrint.hentAntallRader(); i++) {
            for (int j = 0; j < labyrint.hentAntallKolonner(); j++) {
                System.out.println(løsning[j][i]);
                if (løsning[j][i] == true) {
                    System.out.println("sant");
                    brett[j][i].setStyle("-fx-base: #f40606;");
                }
            }
        }
    }

    private Labyrint fraFil(String filnavn) {
        try {
            File fil = new File(filnavn);
            Labyrint labyrint = Labyrint.lesFraFil(fil); 
            return labyrint;     
        } catch (FileNotFoundException ex) {
            return null;
        } 
    }

    private void nullstillBrett() {
        for (Rute[] rad : brett) {
            for (Rute rute : rad) {
                if (rute.charTilTegn() == '.') {
                    rute.setStyle("-fx-base: #ffffff;");
                }
                else {
                    rute.setStyle("-fx-base: #000000;");
                }
            }
        }
    }
    private void printLosningNr() {
        statusinfo.setText("Viser løsning nr: " + (losningTeller + 1));
    }

    class TrykkRuteBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            trykkPaaRute((Rute) e.getSource());
        }
    }

    class TrykkNesteBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            trykkNeste();
        }
    }

    class TrykkForrigeBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            trykkForrige();
        }
    }

    class StoppBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Platform.exit();
        }
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
            losning[x][y] = true;
        }
        return losning;
    }
}