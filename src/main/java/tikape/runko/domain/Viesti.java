package tikape.runko.domain;

//import java.sql.Date;

public class Viesti {

    private Integer id;
    private String sisalto;
    //private Date pvm;
    private String lahettaja;
    private int aihe_id;

    public Viesti(Integer id, String sisalto, String lahettaja, int aihe_id) {
        this.id = id;
        this.sisalto = sisalto;
        this.lahettaja = lahettaja;
        this.aihe_id = aihe_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
    
    public String getLahettaja() {
        return lahettaja;
    }

    public void setLahettaja(String lahettaja) {
        this.lahettaja = lahettaja;
    }
    
    public Integer getAihe_Id() {
        return aihe_id;
    }

    public void setAihe_Id(Integer aihe_id) {
        this.id = aihe_id;
    }
    
//    public String getPvm() {
//        return pvm;
//    }
//
//    public void setPvm(Date pvm) {
//        this.pvm = pvm;
//    }

}
