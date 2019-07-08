package pl.britenet.parsers;

import pl.britenet.cutter.BufferCutter;
import pl.britenet.mapper.csv.CsvToObjectMapper;

public interface CSVParseable {

    CsvToObjectMapper getCSVMapper();
    BufferCutter getCSVCutter();

}
