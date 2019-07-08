package pl.britenet.assembly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.britenet.Application;
import pl.britenet.cutter.BufferCutter;
import pl.britenet.db.DBWriteable;
import pl.britenet.entity.Contact;
import pl.britenet.entity.Customer;
import pl.britenet.parsers.CSVParseable;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerCSVAssembler extends Assembler {

    private CSVParseable parseable;

    @Autowired
    public CustomerCSVAssembler(CSVParseable parseable, DBWriteable dbWriteable) {
        super(".csv", dbWriteable);
        this.parseable = parseable;
    }

    @Override
    public void parseFile(Path path, int buffer) throws IOException {
        createTables();
        Optional<String> stringOptional = Optional.of("");
        BufferCutter cutter = parseable.getCSVCutter();

        while (!EOF) {

            char[] readed = reader.readFixedBytes(path, buffer, skippedBuffer);
            String complete = cutter.getCompleteBuffer(stringOptional.get() + String.valueOf(readed));
            stringOptional = cutter.getPartialBuffer(String.valueOf(readed));
            List<Customer> all = parseable.getCSVMapper().mapToObjects(complete);

            try {
                Connection conn;
                PreparedStatement st;
                conn = DriverManager.getConnection(Application.DATABASE_URL, Application.DATABASE_USER, Application.DATABASE_PASSWORD);
                int customerIndex = 1;
                int contactIndex = 1;
                for (Customer customer : all) {
                    customer.setId(customerIndex++);
                    st = customer.getInsertSQL(conn);
                    st.execute();
                    for (Contact contact : customer.getContacts()){
                        contact.setId(contactIndex++);
                        contact.setId_customer(customerIndex);
                        st = contact.getInsertSQL(conn);
                        st.execute();
                    }
                }
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            skippedBuffer += buffer;
            EOF = !stringOptional.isPresent();
        }
    }

    @Override
    public String getExtension() {
        return ".csv";
    }
}
