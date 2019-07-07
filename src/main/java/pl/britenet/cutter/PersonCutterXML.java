package pl.britenet.cutter;

import java.util.Optional;

public class PersonCutterXML implements BufferCutter {

    private static final String STARTER = "<persons>";
    private static final String FINISHER = "</persons>";
    private static final String CUTTER = "</person>";

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
