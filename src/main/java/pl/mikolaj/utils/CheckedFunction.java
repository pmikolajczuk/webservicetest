package pl.mikolaj.utils;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
