package tikape.runko.domain;

import java.sql.Timestamp;

public class Viesti {

    private Integer viestinro;
    private String sisalto;
    private Timestamp pvm;
    private int aiheid;

    public Viesti(Integer viestinro, int aiheid, String sisalto, Timestamp pvm) {
        this.viestinro = viestinro;
        this.aiheid = aiheid;
        this.sisalto = sisalto;
        this.pvm = pvm;
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
        return pvm.toString();
    }

}
