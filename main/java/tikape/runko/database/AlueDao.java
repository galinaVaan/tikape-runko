/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

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
        Connection connection = database.getConnection();
        connection.createStatement().executeUpdate("INSERT INTO Alue (nimi) VALUES ('" + a.getNimi() + "');");
        
        return a;
    }
    
    public Integer countViesti(Alue a) throws SQLException {
//        Connection connection = database.getConnection();
        int count = 0;
        List<Aihe> aiheet = aiheDao.findByAlue(a.getAlueid());
        for (Aihe aihe : aiheet) {
            count += aiheDao.countViesti(aihe.getAiheid());
        }
        return count;
    }
}
