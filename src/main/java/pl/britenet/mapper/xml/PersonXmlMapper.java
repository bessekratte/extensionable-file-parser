package pl.britenet.mapper.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pl.britenet.entity.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PersonXmlMapper implements XmlToObjectMapper<Person> {

    @Override
    public List<Person> mapToObjects(String values) {

        XmlMapper xmlMapper = new XmlMapper();
        try {
            return Arrays.asList(xmlMapper.readValue(values, Person[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: 06.07.2019
        }
    }
}
