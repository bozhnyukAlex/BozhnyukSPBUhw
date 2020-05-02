package org.app;

public class BasicLocaleService implements LocaleService{
    @Override
    public void locale() {
        System.out.println("Basic");
    }

    @Override
    public void debug() {
        System.out.println("SPEAKS...BASICS??");
    }
}
