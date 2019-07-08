package pl.britenet.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBInsertable {
    PreparedStatement getInsertSQL(Connection conn) throws SQLException;
}
