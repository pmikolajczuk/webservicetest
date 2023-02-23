package pl.mikolaj.webservicetest.utils;

import java.util.function.Consumer;

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
}
