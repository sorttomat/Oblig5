class HvitRute extends Rute {
    protected HvitRute(int kolonne, int rad, Labyrint labyrint) {
        super(kolonne, rad, labyrint);
    }

    @Override
    protected char charTilTegn() {
        return '.';
    }

    @Override
    protected boolean erAapning() {
        return false;
    }
}