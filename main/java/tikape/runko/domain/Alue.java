package tikape.runko.domain;

public class Alue {

    private Integer alueid;
    private String nimi;

    public Alue(Integer alueid, String nimi) {
        this.alueid = alueid;
        this.nimi = nimi;
    }

    public Integer getAlueid() {
        return alueid;
    }

    public void setAlueid(Integer alueid) {
        this.alueid = alueid;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
