package org.app;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface LocaleService {
    void locale();
    void debug();
    static List<LocaleService> getServices(ModuleLayer layer) {
        return ServiceLoader.load(layer, LocaleService.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}
