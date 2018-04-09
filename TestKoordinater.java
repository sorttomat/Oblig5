import java.io.File;

class TestKoordinater {
    public static void main(String[] args) {
        int kolonner = 4;
        int rader = 5;
        String[][] bokstaver = new String[kolonner][rader];

        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                bokstaver[j][i] = "O";
            }
        }
        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                System.out.print(bokstaver[j][i]);
            }
            System.out.println();
        }
        File file = new File("labyrint4.txt");
        Labyrint.lesFraFil(file);

        Liste<String> liste = Labyrint.finnUtveiFra(35, 72);
        // for (String string : liste) {
        //     System.out.println(string);
        // }
    }
}