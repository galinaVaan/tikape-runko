package tikape.runko.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Viesti {

    private Integer viestinro;
    private int aiheid;
    private String sisalto;
    private Timestamp pvm;
    private String lahettaja;

    public Viesti(Integer viestinro, int aiheid, String sisalto, Timestamp pvm, String lahettaja) {
        this.viestinro = viestinro;
        this.aiheid = aiheid;
        this.sisalto = sisalto;
        this.pvm = pvm;
        this.lahettaja = lahettaja;
    }

    public Integer getId() {
        return viestinro;
    }

    public void setId(Integer viestinro) {
        this.viestinro = viestinro;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
    
    public Integer getAiheid() {
        return aiheid;
    }

    public void setAiheid(Integer aihe_id) {
        this.aiheid = aihe_id;
    }
    
    public Timestamp getPvm() {
        return pvm;
    }

    public void setPvm(Timestamp pvm) {
        this.pvm = pvm;
    }
    
    public String getPvmstring() {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
        String dateString;
        if (pvm.toString() == "") {
           dateString = "tyhjä";
        } else {
           dateString = sdf.format(pvm);
        }
        return dateString;
    }
    
    public String getLahettaja() {
        return lahettaja;
    }

    public void setLahettaja(String lahettaja) {
        this.lahettaja = lahettaja;
    }

}
