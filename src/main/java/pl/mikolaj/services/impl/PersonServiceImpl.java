package pl.mikolaj.services.impl;

import org.springframework.stereotype.Service;
import pl.mikolaj.beans.Person;
import pl.mikolaj.dao.PersonDao;
import pl.mikolaj.services.PersonService;

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
}
