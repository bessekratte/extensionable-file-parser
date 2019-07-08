package pl.britenet.mapper.csv;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import org.springframework.stereotype.Component;
import pl.britenet.entity.Contact;
import pl.britenet.entity.Customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonCsvMapper implements CsvToObjectMapper<Customer> {


    @Override
    public List<Customer> mapToObjects(String values) {

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

        try {
            String[][] rows = mapper.readValue(values, String[][].class);
            List<Customer> customers = new ArrayList<>();

            for (String[] row : rows) {
                Customer customer = new Customer();
                customer.setContacts(new ArrayList<>());
                int pointer = 0;

                for (String s : row) {
                    switch (pointer) {
                        case 0:
                            customer.setName(s);
                            break;
                        case 1:
                            customer.setSurname(s);
                            break;
                        case 2:
                            if (s.equals("")) customer.setAge(0);
                            else customer.setAge(Integer.valueOf(s));
                            break;
                        case 3:
                            customer.setCity(s);
                            break;
                        default:
                            Contact contact = new Contact();
                            contact.setContact(s);
                            customer.getContacts().add(contact);
                            break;
                    }
                    pointer++;
                }
                customers.add(customer);
            }
            return customers;
        } catch (IOException e) {
            // TODO: 07.07.2019
            throw new RuntimeException(e);
        }
    }
}
