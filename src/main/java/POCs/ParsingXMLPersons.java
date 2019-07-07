package POCs;

import pl.britenet.cutter.PersonCutterXML;
import pl.britenet.entity.Person;
import pl.britenet.mapper.xml.PersonXmlMapper;
import pl.britenet.mapper.xml.XmlToObjectMapper;
import pl.britenet.files.FixedBufferReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ParsingXMLPersons {

    /**
     * założenia:
     * format: .xml
     * klasa: Person
     */

    public static final int DEFAULT_BUFFER = 200;
    public static final Path FILE_PATH = Paths.get("test/persons.xml");

    public static void main(String[] args) throws IOException {

        int skippedBuffor = 0;
        FixedBufferReader reader = new FixedBufferReader();
        Optional<String> stringOptional = Optional.of("");
        boolean EOF = false;

        while (!EOF) {

            // continue reading
            char[] readed = reader.readFixedBytes(FILE_PATH, DEFAULT_BUFFER, skippedBuffor);
            PersonCutterXML personCutterXML = new PersonCutterXML();
            String gotowy = personCutterXML.getCompleteBuffer(stringOptional.get() + String.valueOf(readed));
            stringOptional = personCutterXML.getPartialBuffer(String.valueOf(readed));


            // map rest
            XmlToObjectMapper<Person> mapper = new PersonXmlMapper();
            List<Person> list = mapper.mapToObjects(gotowy);
            System.out.println(list);
            skippedBuffor += DEFAULT_BUFFER;
            EOF = !stringOptional.isPresent();
        }
    }

}
