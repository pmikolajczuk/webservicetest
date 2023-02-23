package pl.mikolaj.webservicetest.services;

import pl.mikolaj.webservicetest.beans.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPeople();
    List<Person> getAllPeople(int pageSize, int pageIndex);
    Person findById(int id);
    List<Person> findByIds(List<Integer> ids);
    List<Person> findByIds(List<Integer> ids, int pageSize, int pageIndex);
}
