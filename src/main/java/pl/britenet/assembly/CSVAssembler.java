package pl.britenet.assembly;

import pl.britenet.PrzykladowaEncja;
import pl.britenet.cutter.BufferCutter;
import pl.britenet.entity.Person;
import pl.britenet.files.FixedBufferReader;
import pl.britenet.parsers.CSVParseAble;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class CSVAssembler extends Assembler {

    private static final int DEFAULT_BUFFER = 101;
    private static final Path FILE_PATH = Paths.get("test/persons.csv");

    private CSVParseAble csvAssembler = new PrzykladowaEncja();
    private FixedBufferReader reader = new FixedBufferReader();
    private boolean EOF = false;
    private int skippedBuffor = 0;

    public void doWork() throws IOException {
        Optional<String> stringOptional = Optional.of("");

        while (!EOF) {

            char[] readed = reader.readFixedBytes(FILE_PATH, DEFAULT_BUFFER, skippedBuffor);
            BufferCutter cutter = csvAssembler.getCSVCutter();
            String gotowy = cutter.getCompleteBuffer(stringOptional.get() + String.valueOf(readed));
            stringOptional = cutter.getPartialBuffer(String.valueOf(readed));
            List<Person> all = csvAssembler.getCSVMapper().mapToObjects(gotowy);
            System.out.println(all);
            skippedBuffor += DEFAULT_BUFFER;
            EOF = !stringOptional.isPresent();
        }

    }

    public static void main(String[] args) throws IOException{
        CSVAssembler assembler = new CSVAssembler();
        assembler.doWork();
    }
}
