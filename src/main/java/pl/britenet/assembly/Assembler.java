package pl.britenet.assembly;


import pl.britenet.Application;
import pl.britenet.db.DBWriteable;
import pl.britenet.files.FixedBufferReader;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Assembler {

    final FixedBufferReader reader = new FixedBufferReader();
    DBWriteable dbWriteable;
    boolean EOF = false;
    int skippedBuffer = 0;

    public Assembler(DBWriteable dbWriteable) {
        this.dbWriteable = dbWriteable;
    }

    public abstract void parseFile(Path path, int buffer) throws IOException;
    public abstract String getExtension();

    void createTables() {
        String[] mainCreateTableSQL = dbWriteable.getCreateTableStatements();

        Connection conn;
        PreparedStatement st;
        try {
            conn = DriverManager.getConnection(Application.DATABASE_URL, Application.DATABASE_USER, Application.DATABASE_PASSWORD);
            for (String sql : mainCreateTableSQL) {
                st = conn.prepareStatement(sql);
                st.execute();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
