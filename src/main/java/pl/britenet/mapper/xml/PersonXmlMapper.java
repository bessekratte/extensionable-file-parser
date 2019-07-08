package pl.britenet.mapper.xml;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import pl.britenet.entity.Customer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonXmlMapper implements XmlToObjectMapper<Customer> {

    @Override
    public List<Customer> mapToObjects(String values) {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        try {
            return Arrays.asList(xmlMapper.readValue(values, Customer[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: 06.07.2019
        }
    }
}
