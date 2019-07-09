package pl.britenet.assembly;


import org.springframework.beans.factory.annotation.Autowired;
import pl.britenet.Application;
import pl.britenet.db.DBWriteable;
import pl.britenet.files.FixedBufferReader;
import pl.britenet.properties.PropertyResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Assembler is a class which is inherited by other classes that want to parse file and write content to DB
 */
public abstract class Assembler {

    static final String DATABASE_URL;
    static final String DATABASE_USER;
    static final String DATABASE_PASSWORD;

    @Autowired
    FixedBufferReader reader;
    DBWriteable dbWriteable;
    boolean EOF = false;
    int skippedBuffer = 0;

    static {
        DATABASE_URL = PropertyResolver.getProperty("database.url");
        DATABASE_USER = PropertyResolver.getProperty("database.user");
        DATABASE_PASSWORD = PropertyResolver.getProperty("database.password");
    }

    public Assembler(FixedBufferReader reader, DBWriteable dbWriteable) {
        this.dbWriteable = dbWriteable;
    }

    void createTables() {
        String[] createTableSqls = dbWriteable.getCreateTableStatements();
        Connection conn;
        PreparedStatement st;
        try {
            conn = DriverManager.getConnection(Assembler.DATABASE_URL, Assembler.DATABASE_USER, Assembler.DATABASE_PASSWORD);
            for (String sql : createTableSqls) {
                st = conn.prepareStatement(sql);
                st.execute();
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void parseFile(Path path, int buffer) throws IOException;
    public abstract String getExtension();
}
