package pl.mikolaj.dao.impl;

import org.junit.jupiter.api.Test;
import pl.mikolaj.beans.Car;
import pl.mikolaj.dao.CarDao;

import java.io.IOException;

public class CarDaoImplTest {
    CarDao carDao = new CarDaoImpl();

    @Test
    public void testSaveCar() throws IOException {
        Car honda = new Car(1002,"Honda","Civic", "Red", 2018, 95000.00);
        carDao.saveCar(honda);
    }
}
