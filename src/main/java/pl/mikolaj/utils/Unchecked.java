package pl.mikolaj.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class Unchecked {

    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T, R> Function<T, R> function(CheckedFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
