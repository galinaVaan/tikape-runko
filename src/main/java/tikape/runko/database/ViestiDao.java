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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE viestinro = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer viestinro = rs.getInt("viestinro");
        String sisalto = rs.getString("sisalto");
        int aiheid = rs.getInt("aiheid");
        
        Date date = rs.getDate("pvm");
        Timestamp pvm = new Timestamp(date.getTime());

        Viesti o = new Viesti(viestinro, aiheid, sisalto, pvm);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer viestinro = rs.getInt("viestinro");
            String sisalto = rs.getString("sisalto");
            int aiheid = rs.getInt("aiheid");
            Date date = rs.getDate("pvm");
            Timestamp pvm = new Timestamp(date.getTime());
            
            viestit.add(new Viesti(viestinro, aiheid, sisalto, pvm));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public Viesti create(Viesti v) throws SQLException {
        Connection connection = database.getConnection();
        connection.createStatement().executeUpdate("INSERT INTO Viesti (aiheid, sisalto, pvm) VALUES (" + v.getAiheid() + ", '" + v.getSisalto() + "', '" + v.getPvm() + "');");
        
        return v;
    }
    
    public List<Viesti> findByAihe(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE aiheid = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();        
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer viestinro = rs.getInt("viestinro");
            String sisalto = rs.getString("sisalto");
            int aiheid = rs.getInt("aiheid");
            Date date = rs.getDate("pvm");
            Timestamp pvm = new Timestamp(date.getTime());
            
            viestit.add(new Viesti(viestinro, aiheid, sisalto, pvm));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

}
