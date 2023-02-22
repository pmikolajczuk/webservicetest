package pl.mikolaj.services.impl;

import org.springframework.stereotype.Service;
import pl.mikolaj.beans.Person;
import pl.mikolaj.dao.PersonDao;
import pl.mikolaj.services.PersonService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

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
        return getPage(this::getAllPeople, pageSize, pageIndex);
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
        return getPage(() -> findByIds(ids), pageSize, pageIndex);
    }

    private List<Person> getPage(Supplier<List<Person>> dataProvider, int pageSize, int pageIndex) {
        int firstItemIndex = pageSize * pageIndex;
        int lastItemIndex = pageSize * (pageIndex + 1) ;
        List<Person> allPeople = dataProvider.get();

        if(firstItemIndex >= allPeople.size()) {
            return Collections.emptyList();
        }

        if(lastItemIndex > allPeople.size()) {
            lastItemIndex = allPeople.size();
        }

        return allPeople.subList(firstItemIndex, lastItemIndex);
    }
}
