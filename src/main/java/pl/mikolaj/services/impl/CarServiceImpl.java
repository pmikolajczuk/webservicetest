package pl.mikolaj.services.impl;

import org.springframework.stereotype.Service;
import pl.mikolaj.beans.Car;
import pl.mikolaj.dao.CarDao;
import pl.mikolaj.services.CarService;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarDao carDao;

    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    @Override
    public Car findById(int id) {
        return carDao.findById(id);
    }

    @Override
    public Car createCar(Car car) {
        return carDao.saveCar(car);

    }

    @Override
    public Car updateCar(Car car) {
        return carDao.saveCar(car);
    }

    @Override
    public void delete(int id) {
        carDao.delete(id);
    }
}
