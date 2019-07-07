package pl.britenet.cutter;

import java.util.Optional;

public interface BufferCutter {

    String getCompleteBuffer(String buffer);
    Optional<String> getPartialBuffer(String buffer);
}
