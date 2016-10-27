package tikape.runko;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AiheDao;
import tikape.runko.database.Database;
import tikape.runko.database.AlueDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        Database database = new Database("jdbc:sqlite:tikape_tyo.db");
        //database.init();

        AlueDao alueDao = new AlueDao(database);
        AiheDao aiheDao = new AiheDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            res.redirect("/alueet/");
            return "";
        });

        get("/alueet/", (req, res) -> {
            HashMap map = new HashMap<>();

            List<List> alueList = new ArrayList<List>();

            for (Alue alue : alueDao.findAll()) {
                List<Object> content = new ArrayList<Object>();
                content.add(alue);
                content.add(alueDao.countViesti(alue));
                content.add(alueDao.lastViesti(alue.getAlueid()));
                //content.add("temp");
                alueList.add(content);
            }

            map.put("alueet", alueList);
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        post("/alueet/", (req, res) -> {
            String nimi = req.queryParams("nimi").trim();
            alueDao.create(new Alue(1, nimi));
            int alueid = alueDao.findOneByNimi(nimi).getAlueid();
            res.redirect("/alueet/" + alueid);
            return "";
        });

        get("/alueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            List<List> aiheList = new ArrayList<List>();

            for (Aihe aihe : aiheDao.findByAlue(Integer.parseInt(req.params(":id")))) {
                List<Object> content = new ArrayList<Object>();
                content.add(aihe);
                content.add(aiheDao.countViesti(aihe.getAiheid()));
                content.add(aiheDao.lastViesti(aihe.getAiheid()));
                aiheList.add(content);
            }
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("aiheet", aiheList);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/alueet/:id/aihe/:aiheid", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihe", aiheDao.findOne(Integer.parseInt(req.params(":aiheid"))));
            int sivu = 1;
            if (req.queryParams("sivu") != null) {
                sivu = Integer.parseInt(req.queryParams("sivu"));
            }
            List<Viesti> viestit = viestiDao.findByAihe(Integer.parseInt(req.params(":aiheid")), sivu);
            map.put("viestit", viestit);
            map.put("sivunumero", sivu);
            map.put("sivuplus", sivu + 1);
            if (sivu > 1) {
                map.put("sivuminus", sivu - 1);
            } else {
                map.put("sivuminus", sivu);
            }

            return new ModelAndView(map, "aihe");
        }, new ThymeleafTemplateEngine());

        post("/alueet/:id/aihe/:aiheid", (req, res) -> {
            Date date = new Date();
            int aiheenid = Integer.parseInt(req.queryParams("aiheenid"));
            String sisalto = req.queryParams("sisalto").trim();
            Timestamp pvm = new Timestamp(date.getTime());
            String lahettaja = req.queryParams("lahettaja");

            sisalto = sisalto.replaceAll("\\<.*?\\>", "");

            viestiDao.create(new Viesti(1, aiheenid, sisalto, pvm, lahettaja));

            int alueid = Integer.parseInt(req.queryParams("alueid"));

            res.redirect("/alueet/" + alueid + "/aihe/" + aiheenid);
            return "";
        });

        post("/alueet/:id", (req, res) -> {
            Date date = new Date();

            int alueid = Integer.parseInt(req.queryParams("alueid"));
            String nimi = req.queryParams("otsikko").trim();
            int aiheid = aiheDao.findAll().size() + 1;
            aiheid = aiheDao.create(new Aihe(aiheid, nimi, alueid)).getAiheid();
            String sisalto = req.queryParams("viesti");
            String lahettaja = req.queryParams("lahettaja");

            sisalto = sisalto.replaceAll("\\<.*?\\>", "");
            lahettaja = lahettaja.replaceAll("\\<.*?\\>", "");

            Timestamp pvm = new Timestamp(date.getTime());

            viestiDao.create(new Viesti(1, aiheid, sisalto, pvm, lahettaja));

            res.redirect("/alueet/" + alueid + "/aihe/" + aiheid);
            return "";
        });
    }
}
