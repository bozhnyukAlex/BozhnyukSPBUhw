package com.company.registers;

public class AtomicSRSWRegister <T> implements Register<T>{
    ThreadLocal<Long> lastStamp;
    ThreadLocal<StampedValue<T>> lastRead;
    StampedValue<T> r_value; // regular SRSW timestamp-value pair
    public AtomicSRSWRegister(T init) {
         r_value = new StampedValue<T>(init);
         lastStamp = new ThreadLocal<Long>() {
            protected Long initialValue() { return 0L; };
         };
         lastRead = new ThreadLocal<StampedValue<T>>() {
             protected StampedValue<T> initialValue() { return r_value; };
         };
    }

    @Override
    public T read() {
        StampedValue<T> value = r_value;
        StampedValue<T> last = lastRead.get();
        StampedValue<T> result = StampedValue.max(value, last);
        lastRead.set(result);
        return result.value;
    }

    @Override
    public void write(T value) {
        long stamp = lastStamp.get() + 1;
        r_value = new StampedValue(stamp, value);
        lastStamp.set(stamp);
    }
}
