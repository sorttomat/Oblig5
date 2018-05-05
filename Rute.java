import javafx.scene.control.Button; 

abstract class Rute extends Button {
    int _kolonne;
    int _rad;
    Labyrint _labyrint;
    Rute _nord;
    Rute _syd;
    Rute _ost;
    Rute _vest;
    boolean _harVaert;
    Liste<String> _utveier;

    protected Rute(int kolonne, int rad, Labyrint labyrint) {
        _kolonne = kolonne;
        _rad = rad;
        _labyrint = labyrint;
        _harVaert = false;
        _utveier = null;
    }

    public int hentKolonne() {
        return _kolonne;
    }

    public int hentRad() {
        return _rad;
    }

    protected boolean harVaert() {
        return _harVaert;
    }

    protected void settVært() {
        _harVaert = true;
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
        return rute._nord != null && rute._nord._harVaert == false; 
    }

    private boolean kanGaaSyd(Rute rute) {
        return rute._syd != null && rute._syd._harVaert == false;
    }

    private boolean kanGaaOst(Rute rute) {
        return rute._ost != null && rute._ost._harVaert == false;
    }

    private boolean kanGaaVest(Rute rute) {
        return rute._vest != null && rute._vest._harVaert == false;
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
            _utveier.leggTil(utvei);
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
        if (_utveier == null) {
            _utveier = new Lenkeliste<String>();
            gaa(_kolonne, _rad, "");
        }
    }

    public Liste<String> hentUtveier() {
        return _utveier;
    }

    public void settBlank() {
        _harVaert = false;
    }

    abstract char charTilTegn();

    abstract boolean erAapning();
}