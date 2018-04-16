abstract class Rute {
    int _kolonne;
    int _rad;
    Labyrint _labyrint;
    Rute _nord;
    Rute _syd;
    Rute _ost;
    Rute _vest;
    boolean harVaert;
    Liste<String> utveier;

    protected Rute(int kolonne, int rad, Labyrint labyrint) { 
        _kolonne = kolonne;
        _rad = rad;
        _labyrint = labyrint;
        harVaert = false;
        utveier = new Lenkeliste<String>();
    }

    protected boolean harVaert() {
        return harVaert;
    }

    protected void settVært() {
        harVaert = true;
    }

    private Rute finnNord() {
        if (_rad - 1 >= 0 && _labyrint.hentRuter()[_kolonne][_rad - 1].charTilTegn() == '.') {
            return _labyrint.hentRuter()[_kolonne][_rad - 1];
        }
        return null;
    }

    private Rute finnSyd() {
        if (_rad + 1 < _labyrint.hentAntallRader() && _labyrint.hentRuter()[_kolonne][_rad + 1].charTilTegn() == '.') {
            return _labyrint.hentRuter()[_kolonne][_rad + 1];
        }
        return null;
    }

    private Rute finnOst() {
        if (_kolonne + 1 < _labyrint.hentAntallKolonner() && _labyrint.hentRuter()[_kolonne + 1][_rad].charTilTegn() == '.') {
            return _labyrint.hentRuter()[_kolonne + 1][_rad];
        }
        return null;
    }

    private Rute finnVest() {
        if (_kolonne - 1 >= 0 && _labyrint.hentRuter()[_kolonne - 1][_rad].charTilTegn() == '.') {
            return _labyrint.hentRuter()[_kolonne - 1][_rad];
        }
        return null;
    }

    private boolean kanGaaNord(Rute rute) {
        return rute._nord != null && rute._nord.harVaert == false; 
    }

    private boolean kanGaaSyd(Rute rute) {
        return rute._syd != null && rute._syd.harVaert == false;
    }

    private boolean kanGaaOst(Rute rute) {
        return rute._ost != null && rute._ost.harVaert == false;
    }

    private boolean kanGaaVest(Rute rute) {
        return rute._vest != null && rute._vest.harVaert == false;
    }

    public void finnAapneNaboer() {
        _nord = finnNord();
        _syd = finnSyd();
        _ost = finnOst();
        _vest = finnVest();
    }

    public void gaa(int kolonne, int rad, String utvei) { //Hvilke koordinater den går FRA.

        Rute forrigeRute = _labyrint.hentRuter()[kolonne][rad];
        forrigeRute.settVært();

        if (forrigeRute.erAapning()) {
            utvei +=  "(" + forrigeRute._kolonne + ", " + forrigeRute._rad + ")";
            this.utveier.leggTil(utvei);
        }
        utvei +=  "(" + forrigeRute._kolonne + ", " + forrigeRute._rad + ") --> ";

        if (kanGaaNord(forrigeRute)) {
            gaa(forrigeRute._nord._kolonne, forrigeRute._nord._rad, utvei);
        }

        if (kanGaaSyd(forrigeRute)) {
            gaa(forrigeRute._syd._kolonne, forrigeRute._syd._rad, utvei);
        }

        if (kanGaaOst(forrigeRute)) {
            gaa(forrigeRute._ost._kolonne, forrigeRute._ost._rad, utvei);
        }

        if (kanGaaVest(forrigeRute)) {
            gaa(forrigeRute._vest._kolonne, forrigeRute._vest._rad, utvei);
        }

    }

    public void finnUtvei() {
        gaa(_kolonne, _rad, "");
    }

    public Liste<String> hentUtveier() {
        return utveier;
    }

    abstract char charTilTegn();

    abstract boolean erAapning();
}