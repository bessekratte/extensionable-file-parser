package pl.britenet.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import pl.britenet.db.DBInsertable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
public class Contact implements DBInsertable {

    private int id;
    private int type;
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

    public void setContact(String contact) {
        this.contact = contact;
        setType();
    }

    public void setType() {
        this.type = ContactType.resolveContactType(this.contact).getOrderNumber();
    }

    @Getter
    enum ContactType{
        EMAIL(1),
        PHONE(2),
        JABBER(3),
        UNKNOWN(0);

        private int orderNumber;

        ContactType(int orderNumber){
            this.orderNumber = orderNumber;
        }

        private static ContactType resolveContactType(String contact){
            return contact.contains("@") ? EMAIL : contact.matches(".*\\d.*") ? PHONE : JABBER;
        }
    }


}
