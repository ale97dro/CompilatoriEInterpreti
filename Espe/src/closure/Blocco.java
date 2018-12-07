package closure;

import java.util.List;

public class Blocco {

    private String etichetta;
    private List<Blocco> sottoBlocchi;
    private String testo;

    public Blocco(String etichetta, String testo, List<Blocco> sottoBlocchi)
    {
        this.etichetta = etichetta;
        this.testo = testo;
        this.sottoBlocchi = sottoBlocchi;
    }

    public String getEtichetta() {
        return etichetta;
    }

    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
