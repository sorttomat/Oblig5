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
        File file = new File("labyrint3.txt");
        Labyrint labyrint = new Labyrint(file);
        //Labyrint.lesFraFil(file);
    }
}