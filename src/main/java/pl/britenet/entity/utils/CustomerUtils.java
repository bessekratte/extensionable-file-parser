package pl.britenet.entity.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.britenet.cutter.PersonCutterCSV;
import pl.britenet.cutter.PersonCutterXML;
import pl.britenet.mapper.csv.CsvToObjectMapper;
import pl.britenet.mapper.xml.XmlToObjectMapper;

@Component
@Getter
public class CustomerUtils {

    private CsvToObjectMapper csvMapper;
    private XmlToObjectMapper xmlMapper;
    private PersonCutterXML xmlCutter;
    private PersonCutterCSV csvCutter;

    @Autowired
    public CustomerUtils(CsvToObjectMapper csvMapper, XmlToObjectMapper xmlMapper, PersonCutterXML xmlCutter, PersonCutterCSV csvCutter) {
        this.csvMapper = csvMapper;
        this.xmlMapper = xmlMapper;
        this.xmlCutter = xmlCutter;
        this.csvCutter = csvCutter;
    }

}
