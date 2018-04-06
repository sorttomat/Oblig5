class Aapning extends HvitRute{
    public Aapning(int kolonne, int rad, Labyrint labyrint) {
        super(kolonne, rad, labyrint);
    }

    @Override
    public char charTilTegn() {
        return 'O';
    }
}