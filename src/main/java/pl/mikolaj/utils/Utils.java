package pl.mikolaj.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Utils {
    public static <T> List<T> loadAllItems(Class<T> clazz, String jsonFilePath) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(jsonFilePath);
            List<T> result = new Gson().fromJson(fileReader, listType);
            fileReader.close();
            return result;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getPage(Supplier<List<T>> dataProvider, int pageSize, int pageIndex) {
        int firstItemIndex = pageSize * pageIndex;
        int lastItemIndex = pageSize * (pageIndex + 1);
        List<T> items = dataProvider.get();

        if (firstItemIndex >= items.size()) {
            return Collections.emptyList();
        }

        if (lastItemIndex > items.size()) {
            lastItemIndex = items.size();
        }
        return items.subList(firstItemIndex, lastItemIndex);
    }
}
