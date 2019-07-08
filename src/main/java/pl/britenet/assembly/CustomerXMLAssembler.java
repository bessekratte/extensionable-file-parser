package pl.britenet.assembly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.britenet.Application;
import pl.britenet.cutter.BufferCutter;
import pl.britenet.db.DBWriteable;
import pl.britenet.entity.Contact;
import pl.britenet.entity.Customer;
import pl.britenet.parsers.XMLParseable;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerXMLAssembler extends Assembler {

    private XMLParseable parseable;

    @Autowired
    public CustomerXMLAssembler(XMLParseable parseable, DBWriteable dbWriteable) {
        super(dbWriteable);
        this.parseable = parseable;
        this.dbWriteable = dbWriteable;
    }

    @Override
    public void parseFile(Path path, int buffer) throws IOException {
        createTables();
        Optional<String> stringOptional = Optional.of("");
        BufferCutter cutter = parseable.getXMLCutter();

        while (!EOF) {

            char[] readed = reader.readFixedBytes(path, buffer, skippedBuffer);
            String gotowy = cutter.getCompleteBuffer(stringOptional.get() + String.valueOf(readed));
            stringOptional = cutter.getPartialBuffer(String.valueOf(readed));
            List<Customer> all = parseable.getXMLMapper().mapToObjects(gotowy);

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
            System.out.println(all);
            skippedBuffer += buffer;
            EOF = !stringOptional.isPresent();
        }
    }

    @Override
    public String getExtension() {
        return ".xml";
    }
}
