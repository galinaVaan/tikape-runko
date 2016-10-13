package tikape.runko;

import java.sql.Timestamp;
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
import tikape.runko.domain.Alue;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:tikape_tyo.db");
        //database.init();

        AlueDao alueDao = new AlueDao(database);
        AiheDao aiheDao = new AiheDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alueet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());
        
        post("/alueet/", (req, res) -> {
            String nimi = req.queryParams("nimi").trim();
            alueDao.create(new Alue(1, nimi));
            
            res.redirect("/alueet/");
            return "";
        });

        get("/alueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("aiheet", aiheDao.findByAlue(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        get("/alueet/:id/aihe/:aiheid", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihe", aiheDao.findOne(Integer.parseInt(req.params(":aiheid"))));
            List<Viesti> viestit = viestiDao.findAll();
            map.put("viestit", viestit);
            return new ModelAndView(map, "aihe");
        }, new ThymeleafTemplateEngine());
        
        post("/alueet/:id/aihe/:aiheid", (req, res) -> {
            Date date = new Date();
            int aiheenid = Integer.parseInt(req.queryParams("aiheenid"));
            String sisalto = req.queryParams("sisalto").trim();
            Timestamp pvm = new Timestamp(date.getTime());
            
            viestiDao.create(new Viesti(1, aiheenid, sisalto, pvm));
            
            res.redirect("/alueet/:id/aihe/" + aiheenid);
            return "";
        });
    }
}
