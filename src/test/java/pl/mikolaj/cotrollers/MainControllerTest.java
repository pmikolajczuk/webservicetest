package pl.mikolaj.cotrollers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mikolaj.webservicetest.beans.CarDTO;
import pl.mikolaj.webservicetest.controllers.MainController;
import pl.mikolaj.webservicetest.dao.CarDao;
import pl.mikolaj.webservicetest.dao.PersonDao;
import pl.mikolaj.webservicetest.dao.impl.CarDaoImpl;
import pl.mikolaj.webservicetest.dao.impl.PersonDaoImpl;
import pl.mikolaj.webservicetest.services.CarService;
import pl.mikolaj.webservicetest.services.PersonService;
import pl.mikolaj.webservicetest.services.impl.CarServiceImpl;
import pl.mikolaj.webservicetest.services.impl.PersonServiceImpl;

public class MainControllerTest {

    MainController mainController;

    @BeforeEach
    public void setUp() {
        CarDao carDao = new CarDaoImpl();
        CarService carService = new CarServiceImpl(carDao);
        PersonDao personDao = new PersonDaoImpl();
        PersonService personService = new PersonServiceImpl(personDao);

        mainController = new MainController(carService, personService);


    }

    @Test
    public void testFindCarById() {
        CarDTO carDto = mainController.findCarById(2);
        System.out.println(carDto);
        Assertions.assertNotNull(carDto);
    }
}
