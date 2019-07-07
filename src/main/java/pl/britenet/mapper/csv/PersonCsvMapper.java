package pl.britenet.mapper.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import pl.britenet.entity.Person;

import java.io.IOException;
import java.util.List;

public class PersonCsvMapper implements CsvToObjectMapper<Person> {



    @Override
    public List<Person> mapToObjects(String values) {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("name")
                .addColumn("age")
                .build();
        try {
            MappingIterator<Person> it = mapper.readerFor(Person.class).with(schema)
                    .readValues(values);
            return it.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: 06.07.2019
        }
    }
}
