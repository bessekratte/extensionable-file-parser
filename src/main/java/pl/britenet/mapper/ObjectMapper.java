package pl.britenet.mapper;

import java.util.List;

public interface ObjectMapper<T> {
    List<T> mapToObjects(String values);
}
