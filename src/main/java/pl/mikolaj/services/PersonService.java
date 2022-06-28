package pl.mikolaj.services;

import pl.mikolaj.beans.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPeople();
    Person findById(int id);
}
