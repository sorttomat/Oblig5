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
import javafx.geometry.Rectangle2D;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.scene.shape.*;

public class LabyrintGUI extends Application {
    File fil;
    Labyrint labyrint;
    Text statusinfo;
    GridPane rutenett;
    Rute[][] brett;
    int losningTeller;
    Lenkeliste<boolean[][]> listeMedUtveierBoolean;
    Button forrigeKnapp;
    Button nesteKnapp;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage teater) {
        try {
            FileChooser filvelger = new FileChooser();
            fil = filvelger.showOpenDialog(null);
            labyrint = Labyrint.lesFraFil(fil);  
        } catch (FileNotFoundException ex) {
        }

        statusinfo = new Text("Velg en rute");
        statusinfo.setFont(new Font(20));
        statusinfo.setX(130);
        statusinfo.setY(430);

        Button stoppknapp = new Button("Stopp");
        stoppknapp.setLayoutX(10);
        stoppknapp.setLayoutY(460);
        StoppBehandler stopp = new StoppBehandler();
        stoppknapp.setOnAction(stopp);

        forrigeKnapp = new Button("Forrige løsning");
        forrigeKnapp.setLayoutX(10);
        forrigeKnapp.setLayoutY(430);
        TrykkForrigeBehandler forrige = new TrykkForrigeBehandler();
        forrigeKnapp.setOnAction(forrige);
        forrigeKnapp.setDisable(true);

        nesteKnapp = new Button("Neste løsning");
        nesteKnapp.setLayoutX(300);
        nesteKnapp.setLayoutY(430);
        TrykkNesteBehandler neste = new TrykkNesteBehandler();
        nesteKnapp.setOnAction(neste);
        nesteKnapp.setDisable(true);

        brett = labyrint.hentRuter();
        TrykkRuteBehandler trykkRute = new TrykkRuteBehandler();

        rutenett = new GridPane();
        for (int i = 0; i < labyrint.hentAntallRader(); i++) {
            for (int j = 0; j < labyrint.hentAntallKolonner(); j++) {
                Rute rute = brett[j][i];
                if (rute.charTilTegn() == '.') {
                    rute.setStyle("-fx-base: #ffffff;");
                    rute.setOnAction(trykkRute);
                    rute.setPrefWidth(30);
                    rute.setPrefHeight(30);
                    rutenett.add(rute, j, i);
                }
                else {
                    Rectangle rec = new Rectangle();
                    rec.setWidth(30);
                    rec.setHeight(30);
                    rutenett.add(rec, j, i);
                }
            }
        }

        rutenett.setLayoutX(10);
        rutenett.setLayoutY(10);
        rutenett.setStyle("-fx-background-color: #000000;");

        Pane kulisser = new Pane();
        kulisser.setPrefSize(400, 500);
        kulisser.getChildren().add(rutenett);
        kulisser.getChildren().add(statusinfo);
        kulisser.getChildren().add(stoppknapp);
        kulisser.getChildren().add(nesteKnapp);
        kulisser.getChildren().add(forrigeKnapp);

        Scene scene = new Scene(kulisser);

        teater.setTitle("Christines fantastiske labyrintspill!");
        teater.setScene(scene);
        teater.show();
    }

    private void trykkPaaRute(Rute rute) { // TODO: disable/enable knapper i metode sammen med printLosningNr og                                          // fargeleggUtvei.
        losningTeller = 0;
        nullstillBrett();

        Liste<String> utveier = labyrint.finnAlleUtveierFra(rute.hentKolonne(), rute.hentRad());
        listeMedUtveierBoolean = new Lenkeliste<boolean[][]>();
        if (utveier.stoerrelse() == 0) {
            statusinfo.setText("Ingen utveier");
            return;
        }
        for (String utvei : utveier) {
            boolean[][] løsning = losningStringTilTabell(utvei, labyrint.hentAntallRader(),
                    labyrint.hentAntallKolonner());
            listeMedUtveierBoolean.leggTil(løsning);
        }
        oppdaterTeater();
    }

    private void oppdaterTeater() {
        printLosningNr();
        fargeleggUtvei(listeMedUtveierBoolean.hent(losningTeller));
        if (losningTeller < listeMedUtveierBoolean.stoerrelse() - 1) {
            nesteKnapp.setDisable(false);
        }
        if (losningTeller == (listeMedUtveierBoolean.stoerrelse() - 1)) {
            nesteKnapp.setDisable(true);
        }
        if (losningTeller > 0) {
            forrigeKnapp.setDisable(false);
        }
        if (losningTeller == 0) {
            forrigeKnapp.setDisable(true);
        }
    }

    private void trykkNeste() {
        if (losningTeller < listeMedUtveierBoolean.stoerrelse() - 1) {
            nullstillBrett();
            losningTeller++;
            oppdaterTeater();
        }
    }

    private void trykkForrige() {
        if (losningTeller > 0) {
            nullstillBrett();
            losningTeller--;
            oppdaterTeater();
        }
    }

    private void fargeleggUtvei(boolean[][] løsning) {
        for (int i = 0; i < labyrint.hentAntallRader(); i++) {
            for (int j = 0; j < labyrint.hentAntallKolonner(); j++) {
                if (løsning[j][i] == true) {
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
        nesteKnapp.setDisable(true);
        forrigeKnapp.setDisable(true);
        for (Rute[] rad : brett) {
            for (Rute rute : rad) {
                if (rute.charTilTegn() == '.') {
                    rute.setStyle("-fx-base: #ffffff;");
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