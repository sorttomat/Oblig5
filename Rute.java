

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

    protected Rute(int kolonne, int rad, Labyrint labyrint) { // naborutene med her?
        _kolonne = kolonne;
        _rad = rad;
        _labyrint = labyrint;
        harVaert = false;
        utveier = new Lenkeliste<String>();
    }

    protected boolean harVaert() {
        return harVaert;
    }

    private Rute finnNord() {
        return null;
        //Hvis rad - 1 >=0 og (kolonne, rad-1) er åpen, returner den ruten (få fra labyrint). Ellers, returner null.
    }

    private Rute finnSyd() {
        return null;
        //Hvis rad + 1 < antall rader og (kolonne, rad + 1) er åoen, returner ruten. Ellers, returner null.
    }

    private Rute finnOst() {
        return null;
        //Hvis kolonne + 1 < antall kolonner og (kolonne + 1, rad) er åpen, returner ruten. Ellers, returner null.
    }

    private Rute finnVest() {
        return null;
        //Hvis kolonne - 1 >=0 og (kolonne - 1, rad) er åpen, returner ruten. Ellers, returner null.
    }

    public void finnNaboer() {
        //Må kalles på etter at alle celler er lest inn og opprettet. Dette gjøres nok i Labyrint.
        _nord = finnNord();
        _syd = finnSyd();
        _ost = finnOst();
        _vest = finnVest();
    }

    public Liste<String> gaa(int kolonne, int rad, String utvei) { //Hvilke koordinater den går FRA.
        // "Lag en metode gaa i Rute. Denne metoden skal kalle gaa på alle naboruter unntatt den som er i den retningen kallet kom fra (for da ville vi gått tilbake til der vi nettopp var)".

        //Marker ruten harVært()
        //Hvis ruten man står på er en åpning (har tegnet 'O', f eks), this.utveier.leggTil(utvei), return. (trenger kanskje ikke return ...)
        //Hvis _nord ikke er null og man ikke har vært der: gaa(_nord sine koordinater)
        //Hvis _syd ikke er null og man ikke har vært der: gaa(_syd sine koordinater)
        //Hvis _ost ikke er null og man ikke har vært der: gaa(_ost sine koordinater)
        //Hvis _vest ikke er null og man ikke har vært der: gaa(_vest sine koordinater)

        //Hver gang man går, utvei += """-->"(kolonne, rad)"
        return null;

    }

    public void finnUtvei() {
        // "Lag så metoden  void finnUtvei() i Rute som finner alle utveier fra ruten ved hjelp av kall på gaa."
        //gaa(this.kolonne, this.rad, "")
    }

    public Liste<String> hentUtveier() {
        return utveier;
    }

    abstract char charTilTegn();
}