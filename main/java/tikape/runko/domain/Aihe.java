package tikape.runko.domain;

public class Aihe {

    private Integer aiheid;
    private String otsikko;
    private int alueid;
    private int count;

    public Aihe(Integer aiheid, String otsikko, int alueid, int count) {
        this.aiheid = aiheid;
        this.otsikko = otsikko;
        this.alueid = alueid;
        this.count = count;
    }

    public Integer getAiheid() {
        return aiheid;
    }

    public void setAiheid(Integer aiheid) {
        this.aiheid = aiheid;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public Integer getAlueid() {
        return alueid;
    }

    public void setAlueid(Integer alueid) {
        this.alueid = alueid;
    }
    
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
