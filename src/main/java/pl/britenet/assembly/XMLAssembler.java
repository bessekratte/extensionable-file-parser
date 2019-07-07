package pl.britenet.assembly;

import pl.britenet.PrzykladowaEncja;
import pl.britenet.cutter.BufferCutter;
import pl.britenet.entity.Person;
import pl.britenet.files.FixedBufferReader;
import pl.britenet.parsers.XMLParseAble;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class XMLAssembler extends Assembler {

    private static final int DEFAULT_BUFFER = 200;
    private static final Path FILE_PATH = Paths.get("test/persons.xml");

    private XMLParseAble xmlAssembler = new PrzykladowaEncja();
    private FixedBufferReader reader = new FixedBufferReader();
    private boolean EOF = false;
    private int skippedBuffor = 0;

    public void doWork() throws IOException {
        Optional<String> stringOptional = Optional.of("");
        BufferCutter cutter = xmlAssembler.getXMLCutter();

        while (!EOF) {

            char[] readed = reader.readFixedBytes(FILE_PATH, DEFAULT_BUFFER, skippedBuffor);
            String gotowy = cutter.getCompleteBuffer(stringOptional.get() + String.valueOf(readed));
            stringOptional = cutter.getPartialBuffer(String.valueOf(readed));
            List<Person> all = xmlAssembler.getXMLMapper().mapToObjects(gotowy);

            System.out.println(all);
            skippedBuffor += DEFAULT_BUFFER;
            EOF = !stringOptional.isPresent();
        }

    }

    public static void main(String[] args) throws IOException{
        XMLAssembler assembler = new XMLAssembler();
        assembler.doWork();
    }
}
