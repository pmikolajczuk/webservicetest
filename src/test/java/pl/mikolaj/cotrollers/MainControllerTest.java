package pl.mikolaj.cotrollers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mikolaj.beans.CarDTO;
import pl.mikolaj.controllers.MainController;
import pl.mikolaj.dao.CarDao;
import pl.mikolaj.dao.PersonDao;
import pl.mikolaj.dao.impl.CarDaoImpl;
import pl.mikolaj.dao.impl.PersonDaoImpl;
import pl.mikolaj.services.CarService;
import pl.mikolaj.services.PersonService;
import pl.mikolaj.services.impl.CarServiceImpl;
import pl.mikolaj.services.impl.PersonServiceImpl;

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
