package pl.mikolaj.utils;

@FunctionalInterface
public interface CheckedConsumer<T> {
    void accept(T t) throws Exception;
}
