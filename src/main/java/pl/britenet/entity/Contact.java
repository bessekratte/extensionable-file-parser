package pl.britenet.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import pl.britenet.db.DBInsertable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
public class Contact implements DBInsertable {

    private int id;
    private int type;

    @JacksonXmlProperty(localName = "phone")
    private String contact;
    private int id_customer;

    @Override
    public PreparedStatement getInsertSQL(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO CONTACTS values(?, ?, ?, ?)");
        ps.setInt(1, this.id);
        ps.setInt(2, this.id_customer);
        ps.setInt(3, this.type);
        ps.setString(4, this.contact);
        return ps;
    }
}
