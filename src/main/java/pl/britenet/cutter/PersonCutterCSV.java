package pl.britenet.cutter;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonCutterCSV implements BufferCutter {

    private static final String FINISHER = "\u0000";
    private static final String CUTTER = "\n";

    @Override
    public String getCompleteBuffer(String buffer) {
        if (buffer.contains(FINISHER))
            return buffer;
        return buffer.substring(0, buffer.lastIndexOf(CUTTER));
    }

    @Override
    public Optional<String> getPartialBuffer(String buffer) {
        if (buffer.contains(FINISHER))
            return Optional.empty();
        return Optional.of(buffer.substring(buffer.lastIndexOf(CUTTER) + CUTTER.length()));
    }
}
