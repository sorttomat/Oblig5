import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {
    class Node {
        //Nøstet klasse, gir meg tilgang til Node innad i Lenkeliste-klassen.
        private Node next;
        private T data;

        protected Node(T d) {
            next = null;
            data = d;
        }

        protected Node getNext() {
            return next;
        }

        protected T getData() {
            return data;
        }

        protected void setNext(Node ny) {
            this.next = ny;
        }

        protected void setData(T nyData) {
            this.data = nyData;
        }
    }

    class LenkelisteIterator implements Iterator<T> {
        private Lenkeliste<T> lenkeliste;
        private Node gjeldende;

        protected LenkelisteIterator(Lenkeliste<T> lListe) {
            lenkeliste = lListe;
            gjeldende = start;
        }

        @Override
        public boolean hasNext() {
            if (stoerrelse() > 0) {
                return gjeldende != null;
            }
            return false;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                Node node = gjeldende;
                gjeldende = gjeldende.next;
                return node.getData();
            }
            return null;
        }

        @Override
        public void remove() {

        }
    }

    protected Node start = null;
    protected int stoerrelse = 0;

    protected void oekStoerrelse() {
        stoerrelse++;
    }

    protected void minskStoerrelse() {
        stoerrelse--;
    }

    protected boolean erTom() {
        //Sjekker om listen er tom.
        if (stoerrelse() == 0) {
            return true;
        }
        return false;
    }

    protected void leggTilHvisTom(T x) {
        //Denne blir kalt paa dersom listen er tom. Da blir noden lagt til som foerste node.
        Node ny = new Node(x);
        start = ny;
        oekStoerrelse();
    }

    protected void leggTilStart(T x) {
        //Denne blir kalt paa dersom noden skal legges til foerst.
        Node ny = new Node(x);
        ny.setNext(start);
        start = ny;
        oekStoerrelse();
    }

    protected void leggTilSlutt(T x) {
        //Denne blir kalt paa dersom noden skal legges til sist.
        Node ny = new Node(x);
        Node p = start;
        while (p.getNext() != null) {
            p = p.getNext();
        }
        p.setNext(ny);
        oekStoerrelse();
    }

    protected void leggTilMidten(int pos, T x) {
        //Denne blir kalt paa dersom noden skal legges til mellom to andre noder.
        Node ny = new Node(x);
        Node p = start;

        for (int i = 1; i < pos; i++) {
            p = p.getNext();
        }
        Node forskyv = p.getNext();
        p.setNext(ny);
        ny.setNext(forskyv);
        oekStoerrelse();
    }

    @Override
    public int stoerrelse() {
        return stoerrelse;
    }

    @Override
    public void leggTil(int pos, T x) {
        //Sjekker hva posisjonen er, og kaller paa de riktige metodene.
        //Situasjonen er forskjellig avhengig av om noden skal legges til i en tom liste, 
        //i starten av en liste, i midten av en liste, eller i slutten av en liste.
        if (pos == 0) {
            leggTilStart(x);
        } else if ((erTom() && pos != 0) || (pos > stoerrelse()) || (pos < 0)) {
            throw new UgyldigListeIndeks(pos);
        }

        else if (pos == stoerrelse()) {
            leggTilSlutt(x);
        }

        else {
            leggTilMidten(pos, x);
        }
    }

    @Override
    public void leggTil(T x) {
        //Legger til i slutten av listen.
        if (erTom()) {
            leggTilHvisTom(x);
        } else {
            leggTilSlutt(x);
        }

    }

    @Override
    public void sett(int pos, T x) {
        //Endrer dataen til noden paa posisjon pos.
        if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        } else {
            Node p = start;
            for (int i = 0; i < pos; i++) {
                p = p.getNext();
            }
            p.setData(x);
        }
    }

    @Override
    public T hent(int pos) {
        //Henter noden paa posisjon pos.
        if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        } else {
            Node p = start;
            for (int i = 0; i < pos; i++) {
                p = p.getNext();
            }
            return p.getData();
        }
    }

    @Override
    public T fjern(int pos) {
        //Fjerner noden paa posisjon pos, og returnerer denne.
        if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        } else {
            Node p = start;
            minskStoerrelse();
            if (pos == 0) {
                Node neste = p.getNext();
                start = neste;
                return p.getData();
            }
            for (int i = 1; i < pos; i++) {
                p = p.getNext();
            }
            Node slett = p.getNext();
            p.setNext(slett.getNext());
            return slett.getData();
        }
    }

    @Override
    public T fjern() {
        //Fjerner den foerste noden.
        if (stoerrelse() == 0) {
            throw new UgyldigListeIndeks(-1);
        } else {
            Node p = start;
            Node neste = p.getNext();
            start = neste;
            minskStoerrelse();
            return p.getData();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LenkelisteIterator(this);
    }

}