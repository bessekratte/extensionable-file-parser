package pl.britenet;

import pl.britenet.cutter.BufferCutter;
import pl.britenet.cutter.PersonCutterCSV;
import pl.britenet.cutter.PersonCutterXML;
import pl.britenet.mapper.csv.CsvToObjectMapper;
import pl.britenet.mapper.csv.PersonCsvMapper;
import pl.britenet.mapper.xml.PersonXmlMapper;
import pl.britenet.mapper.xml.XmlToObjectMapper;
import pl.britenet.parsers.CSVParseAble;
import pl.britenet.parsers.XMLParseAble;


public class PrzykladowaEncja implements XMLParseAble, CSVParseAble {

    private String name;
    private int age;

    private CsvToObjectMapper csvMapper = new PersonCsvMapper();
    private XmlToObjectMapper xmlMapper = new PersonXmlMapper();

    private BufferCutter xmlCutter = new PersonCutterXML();
    private BufferCutter csvCutter = new PersonCutterCSV();


    @Override
    public CsvToObjectMapper getCSVMapper() {
        return this.csvMapper;
    }

    @Override
    public BufferCutter getCSVCutter() {
        return this.csvCutter;
    }

    @Override
    public XmlToObjectMapper getXMLMapper() {
        return this.xmlMapper;
    }

    @Override
    public BufferCutter getXMLCutter() {
        return this.xmlCutter;
    }
}
