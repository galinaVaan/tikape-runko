/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Alue;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;
    private AiheDao aiheDao;

    public AlueDao(Database database) {
        this.database = database;
        this.aiheDao = new AiheDao(database);
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE alueid = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer alueid = rs.getInt("alueid");
        String nimi = rs.getString("nimi");

        Alue o = new Alue(alueid, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Alue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY nimi ASC");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer alueid = rs.getInt("alueid");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(alueid, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    public Alue create(Alue a) throws SQLException {
        if (a.getNimi().isEmpty()) {
            return a;
        }
        Connection connection = database.getConnection();
        PreparedStatement stmt1 = connection.prepareStatement("SELECT nimi FROM Alue WHERE nimi = ?");
        stmt1.setObject(1, a.getNimi());
        ResultSet rs = stmt1.executeQuery();
        if (!rs.next()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue (nimi) VALUES (?);");
            stmt.setObject(1, a.getNimi());
            stmt.execute();

            stmt.close();
        } else {
            a = findOneByNimi(a.getNimi());
        }
        stmt1.close();
        connection.close();
        return a;
    }

    public Integer countViesti(Alue a) throws SQLException {
        Connection connection = database.getConnection();
        int count = 0;
        List<Aihe> aiheet = aiheDao.findByAlue(a.getAlueid());
        for (Aihe aihe : aiheet) {
            count += aiheDao.countViesti(aihe.getAiheid());
        }
        return count;
    }

    public String lastViesti(int alueid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(pvm) AS viimeisin FROM Viesti, Aihe WHERE Viesti.aiheid = Aihe.aiheid AND Aihe.alueid = ?;");
        stmt.setObject(1, alueid);
        ResultSet rs = stmt.executeQuery();
        String dateString;

        if (!lastViestiCheck(alueid)) {
            dateString = "Ei viestejä";
        } else {
            Date date = rs.getDate("viimeisin");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
            dateString = sdf.format(date);
        }

        rs.close();
        stmt.close();
        connection.close();

        return dateString;
    }

    public boolean lastViestiCheck(int alueid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti, Aihe WHERE Viesti.aiheid = Aihe.aiheid AND Aihe.alueid = ?;");
        stmt.setObject(1, alueid);
        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();

        rs.close();
        stmt.close();
        connection.close();

        if (!hasOne) {
            return false;
        } else {
            return true;
        }
    }

    public Alue findOneByNimi(String etsivaNimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE nimi = ?");
        stmt.setObject(1, etsivaNimi);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer alueid = rs.getInt("alueid");
        String nimi = rs.getString("nimi");

        Alue o = new Alue(alueid, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;

    }
}
