package pl.mikolaj.dao;

import pl.mikolaj.beans.Person;

import java.io.IOException;
import java.util.List;

public interface PersonDao {
    List<Person> getAllPeople() throws IOException;
    Person findById(int id) throws IOException;
}
