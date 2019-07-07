package pl.britenet.parsers;

import pl.britenet.cutter.BufferCutter;
import pl.britenet.mapper.xml.XmlToObjectMapper;

public interface XMLParseAble {

    XmlToObjectMapper getXMLMapper();
    BufferCutter getXMLCutter();
}
