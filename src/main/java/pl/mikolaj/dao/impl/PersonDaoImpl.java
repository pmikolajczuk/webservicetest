package pl.mikolaj.dao.impl;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import pl.mikolaj.beans.Person;
import pl.mikolaj.dao.PersonDao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    @Override
    public List<Person> getAllPeople() throws IOException {
        InputStream inputStream = Resources.getResource("people.json").openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Type listType = new TypeToken<ArrayList<Person>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

}
