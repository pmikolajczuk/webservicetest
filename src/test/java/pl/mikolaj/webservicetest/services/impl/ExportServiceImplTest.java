package pl.mikolaj.webservicetest.services.impl;

import org.junit.jupiter.api.Test;
import pl.mikolaj.webservicetest.dao.PersonDao;
import pl.mikolaj.webservicetest.dao.impl.PersonDaoImpl;
import pl.mikolaj.webservicetest.services.ExportService;
import pl.mikolaj.webservicetest.services.PersonService;

import java.util.List;

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