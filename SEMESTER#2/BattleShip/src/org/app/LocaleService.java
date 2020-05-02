package org.app;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public abstract class LocaleService {
    public abstract void locale();
    public abstract String getName();
    public abstract void debug();
}
