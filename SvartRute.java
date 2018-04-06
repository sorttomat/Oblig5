class SvartRute extends Rute {
    protected SvartRute(int kolonne, int rad, Labyrint labyrint) {
        super(kolonne, rad, labyrint);
    }

    @Override
    protected char charTilTegn() {
        return '#';
    }
}