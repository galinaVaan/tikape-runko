package tikape.runko.domain;

public class Aihe {

    private Integer id;
    private String otsikko;
    private int alue_id;

    public Aihe(Integer id, String otsikko, int alue_id) {
        this.id = id;
        this.otsikko = otsikko;
        this.alue_id = alue_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return otsikko;
    }

    public void setNimi(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public Integer getAlue_Id() {
        return alue_id;
    }

    public void setAlue_Id(Integer alue_id) {
        this.alue_id = alue_id;
    }

}
