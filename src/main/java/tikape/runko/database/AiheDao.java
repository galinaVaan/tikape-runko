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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Alue;

public class AiheDao implements Dao<Aihe, Integer> {

    private Database database;

    public AiheDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihe WHERE aiheid = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer aiheid = rs.getInt("aiheid");
        String otsikko = rs.getString("otsikko");
        int alueid = rs.getInt("alueid");
        int count = countViesti(aiheid);

        Aihe o = new Aihe(aiheid, otsikko, alueid, count);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihe");

        ResultSet rs = stmt.executeQuery();
        List<Aihe> aiheet = new ArrayList<>();
        while (rs.next()) {
            Integer aiheid = rs.getInt("aiheid");
            String otsikko = rs.getString("otsikko");
            int alueid = rs.getInt("alueid");
            int count = countViesti(aiheid);

            aiheet.add(new Aihe(aiheid, otsikko, alueid, count));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public List<Aihe> findByAlue(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihe WHERE alueid = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();        
        List<Aihe> aiheet = new ArrayList<>();
        while (rs.next()) {
            Integer aiheid = rs.getInt("aiheid");
            String otsikko = rs.getString("otsikko");
            int alueid = rs.getInt("alueid");
            int count = countViesti(aiheid);

            aiheet.add(new Aihe(aiheid, otsikko, alueid, count));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aiheet;
    }
    
    public Integer countViesti(int aiheid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(viestinro) AS viestit FROM Viesti WHERE aiheid = ? GROUP BY aiheid");
        stmt.setObject(1, aiheid);
        ResultSet rs = stmt.executeQuery();
        
        boolean hasOne = rs.next();
        if (!hasOne) {
            return 0;
        }
        
        int count = rs.getInt("viestit");
        
        rs.close();
        stmt.close();
        connection.close();
        
        return count;
    }
    
    public String lastViesti(int aiheid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(pvm) AS viimeisin FROM Viesti WHERE aiheid = ?;");
        stmt.setObject(1, aiheid);
        ResultSet rs = stmt.executeQuery();
        
        boolean hasOne = rs.next();
        if (!hasOne) {
            return "ei viestejä";
        }
        
        Date date = rs.getDate("viimeisin");
        String dateString;
        if (date.toString() == "") {
           dateString = "tyhjä";
        } else {
           dateString = date.toString(); 
        }
        
        rs.close();
        stmt.close();
        connection.close();
        
        return dateString;
    }
    
    public Aihe create(Aihe a) throws SQLException {
        Connection connection = database.getConnection();
        connection.createStatement().executeUpdate("INSERT INTO Aihe (aiheid, otsikko, alueid) VALUES (" + a.getAiheid() + ", '" + a.getOtsikko() + "', " + a.getAlueid() + ");");
        
        return a;
    }
}
