import java.io.File;
import java.io.FileNotFoundException;

class TestKoordinater {
    public static void main(String[] args) {
        try {
            File file = new File("labyrint3.txt");
            Labyrint labyrint = Labyrint.lesFraFil(file);
            Liste<String> alleUtveier = labyrint.finnAlleUtveierFra(5, 11);    
            Liste<String> utveier = labyrint.finnAlleUtveierFra(11, 11);

            } catch(FileNotFoundException ex) {
        }
    }
}