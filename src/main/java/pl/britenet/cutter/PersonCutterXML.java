package pl.britenet.cutter;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonCutterXML implements BufferCutter {

    private static final String STARTER = "<customer>";
    private static final String FINISHER = "</customers>";
    private static final String CUTTER = "</customer>";

    @Override
    public String getCompleteBuffer(String buffer) {
        StringBuilder completeString = new StringBuilder();

        if (!buffer.contains(STARTER))
            completeString.append(STARTER);
        if (buffer.contains(FINISHER))
            completeString.append(buffer);
        else completeString.append(buffer, 0, buffer.lastIndexOf(CUTTER) + CUTTER.length())
                .append(FINISHER);

        return completeString.toString();
    }

    @Override
    public Optional<String> getPartialBuffer(String buffer) {
        if (buffer.contains(FINISHER))
            return Optional.empty();

        return Optional.of(buffer.substring(buffer.lastIndexOf(CUTTER) + CUTTER.length()));
    }
}
