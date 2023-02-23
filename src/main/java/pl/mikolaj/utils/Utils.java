package pl.mikolaj.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Utils {
    public static <T> List<T> getPage(Supplier<List<T>> dataProvider, int pageSize, int pageIndex) {
        int firstItemIndex = pageSize * pageIndex;
        int lastItemIndex = pageSize * (pageIndex + 1) ;
        List<T> items = dataProvider.get();

        if(firstItemIndex >= items.size()) {
            return Collections.emptyList();
        }

        if(lastItemIndex > items.size()) {
            lastItemIndex = items.size();
        }

        return items.subList(firstItemIndex, lastItemIndex);
    }
}
