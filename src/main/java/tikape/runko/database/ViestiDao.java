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
        String lahettaja = rs.getString("lahettaja");

        Viesti o = new Viesti(viestinro, aiheid, sisalto, pvm, lahettaja);

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
            String lahettaja = rs.getString("lahettaja");

            viestit.add(new Viesti(viestinro, aiheid, sisalto, pvm, lahettaja));
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
        if (v.getSisalto().isEmpty()) {
            return v;
        }
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (aiheid, sisalto, pvm, lahettaja) VALUES (?, ?, ?, ?);");
        stmt.setObject(1, v.getAiheid());
        stmt.setObject(2, v.getSisalto());
        stmt.setObject(3, v.getPvm());
        stmt.setObject(4, v.getLahettaja());

        stmt.execute();

        stmt.close();
        connection.close();
        return v;
    }

    public List<Viesti> findByAihe(Integer key, Integer limit) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE aiheid = ? LIMIT ?, ?");
        stmt.setObject(1, key);
        stmt.setObject(2, (limit * 10) - 10);
        stmt.setObject(3, limit * 10);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer viestinro = rs.getInt("viestinro");
            String sisalto = rs.getString("sisalto");
            int aiheid = rs.getInt("aiheid");
            Date date = rs.getDate("pvm");
            Timestamp pvm = new Timestamp(date.getTime());
            String lahettaja = rs.getString("lahettaja");

            viestit.add(new Viesti(viestinro, aiheid, sisalto, pvm, lahettaja));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

}
