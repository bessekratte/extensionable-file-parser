package pl.britenet.parsers;

import pl.britenet.cutter.BufferCutter;
import pl.britenet.mapper.xml.XmlToObjectMapper;

public interface XMLParseable {

    XmlToObjectMapper getXMLMapper();
    BufferCutter getXMLCutter();
}
