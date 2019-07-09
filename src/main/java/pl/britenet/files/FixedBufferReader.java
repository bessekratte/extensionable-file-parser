package pl.britenet.files;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FixedBufferReader {

    public char[] readFixedBytes(Path file, int buffor, int toSkip) throws IOException {
        BufferedReader reader = Files.newBufferedReader(file);
        char[] arr = new char[buffor];
        reader.skip(toSkip);
        reader.read(arr, 0, buffor);
        return arr;
    }
}