package pl.mikolaj.webservicetest.services.impl;

import org.springframework.stereotype.Service;
import pl.mikolaj.utils.Utils;
import pl.mikolaj.webservicetest.beans.Person;
import pl.mikolaj.webservicetest.dao.PersonDao;
import pl.mikolaj.webservicetest.services.PersonService;

import java.io.IOException;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public List<Person> getAllPeople() {
        try {
            return personDao.getAllPeople();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Person> getAllPeople(int pageSize, int pageIndex) {
        return Utils.getPage(this::getAllPeople, pageSize, pageIndex);
    }

    @Override
    public Person findById(int id) {
        try {
            return personDao.findById(id);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Person> findByIds(List<Integer> ids) {
        return getAllPeople()
                .stream()
                .filter(person -> ids.contains(person.getId()))
                .toList();
    }

    @Override
    public List<Person> findByIds(List<Integer> ids, int pageSize, int pageIndex) {
        return Utils.getPage(() -> findByIds(ids), pageSize, pageIndex);
    }

}
