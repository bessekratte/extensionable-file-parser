package pl.britenet.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.britenet.cutter.BufferCutter;
import pl.britenet.db.DBWriteable;
import pl.britenet.entity.utils.CustomerUtils;
import pl.britenet.mapper.csv.CsvToObjectMapper;
import pl.britenet.mapper.xml.XmlToObjectMapper;
import pl.britenet.parsers.CSVParseable;
import pl.britenet.parsers.XMLParseable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Data
@Component
public class Customer implements XMLParseable, CSVParseable, DBWriteable {

    private int id;
    private String name;
    private String surname;
    private int age;
    private String city;
    private List<String> contacts;
    @Autowired
    private CustomerUtils utils;

    @Override
    public CsvToObjectMapper getCSVMapper() {
        return utils.getCsvMapper();
    }

    @Override
    public BufferCutter getCSVCutter() {
        return utils.getCsvCutter();
    }

    @Override
    public XmlToObjectMapper getXMLMapper() {
        return utils.getXmlMapper();
    }

    @Override
    public BufferCutter getXMLCutter() {
        return utils.getXmlCutter();
    }

    @Override
    public String[] getCreateTableStatements() {
        String[] sqls = new String[2];
        sqls[0] =  "CREATE TABLE IF NOT EXISTS CUSTOMERS " +
                "(ID INTEGER PRIMARY KEY, " +
                "NAME VARCHAR, " +
                "SURNAME VARCHAR, " +
                "AGE INTEGER);";

        sqls[1] = "CREATE TABLE IF NOT EXISTS CONTACTS " +
                "(ID INTEGER PRIMARY KEY, " +
                "ID_CUSTOMER INTEGER, " +
                "TYPE INTEGER, " +
                "CONTACT VARCHAR, " +
                "FOREIGN KEY(ID_CUSTOMER) REFERENCES CUSTOMERS(ID));";
        return sqls;
    }

    @Override
    public PreparedStatement getInsertSQL(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMERS values(?, ?, ?, ?)");
        ps.setInt(1, this.id);
        ps.setString(2, this.name);
        ps.setString(3, this.surname);
        ps.setInt(4, this.age);
        return ps;
    }
}
