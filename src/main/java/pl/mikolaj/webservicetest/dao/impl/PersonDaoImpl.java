package pl.mikolaj.webservicetest.dao.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;
import pl.mikolaj.webservicetest.beans.Person;
import pl.mikolaj.webservicetest.dao.PersonDao;
import pl.mikolaj.webservicetest.exceptions.ObjectNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {
    private static final String PEOPLE_FILE_PATH = "src\\main\\resources\\people.json";

    @Override
    public List<Person> getAllPeople() throws IOException {
        Type listType = new TypeToken<ArrayList<Person>>() {}.getType();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(PEOPLE_FILE_PATH);
            List<Person> result = new Gson().fromJson(fileReader, listType);

            return result;
        } finally {
            fileReader.close();
        }
    }

    @Override
    public Person findById(int id) throws IOException {
        return getAllPeople().stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(id));
    }

}
