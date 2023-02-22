package pl.mikolaj.services.impl;

import org.junit.jupiter.api.Test;
import pl.mikolaj.dao.PersonDao;
import pl.mikolaj.dao.impl.PersonDaoImpl;
import pl.mikolaj.services.ExportService;
import pl.mikolaj.services.PersonService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportServiceImplTest {
    PersonDao personDao = new PersonDaoImpl();
    PersonService personService = new PersonServiceImpl(personDao);
    ExportService exportService = new ExportServiceImpl(personService);

    @Test
    void exportAllPeople() {
        exportService.exportAllPeople();
    }

    @Test
    void exportPeopleByIds() {
        List<Integer> ids = List.of(1,2,3,4,5,6,7,8,9,10,11);
        exportService.exportPeopleByIds(ids);
    }
}