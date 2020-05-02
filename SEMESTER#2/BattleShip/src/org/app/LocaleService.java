package org.app;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface LocaleService {
    void locale();
    String getName();
}
