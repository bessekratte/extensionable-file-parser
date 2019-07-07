package POCs;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import pl.britenet.cutter.BufferCutter;
import pl.britenet.cutter.PersonCutterCSV;
import pl.britenet.cutter.PersonCutterXML;
import pl.britenet.entity.Person;
import pl.britenet.files.FixedBufferReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ParsingCSVPersons {

    /**
     * założenia:
     * format: .csv
     * klasa: Person
     */

    public static final int DEFAULT_BUFFER = 101;
    public static final Path FILE_PATH = Paths.get("test/persons.csv");

    public static void main(String[] args) throws IOException {

        int skippedBuffor = 0;
        FixedBufferReader reader = new FixedBufferReader();
        Optional<String> stringOptional = Optional.of("");
        boolean EOF = false;

        while (!EOF) {
            // continue reading
            char[] readed = reader.readFixedBytes(FILE_PATH, DEFAULT_BUFFER, skippedBuffor);

            BufferCutter cutter = new PersonCutterCSV();
            String gotowy = cutter.getCompleteBuffer(stringOptional.get() + String.valueOf(readed));
            stringOptional = cutter.getPartialBuffer(String.valueOf(readed));

            // map rest
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.builder()
                    .addColumn("name")
                    .addColumn("age")
                    .build();
            MappingIterator<Person> it = mapper.readerFor(Person.class).with(schema)
                    .readValues(gotowy);
            List<Person> all = it.readAll();
            System.out.println(all);
            skippedBuffor += DEFAULT_BUFFER;
            EOF = !stringOptional.isPresent();
        }
    }
}
