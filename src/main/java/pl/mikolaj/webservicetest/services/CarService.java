package pl.mikolaj.webservicetest.services;

import pl.mikolaj.webservicetest.beans.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    Car findById(int id);

    Car createCar(Car car);

    Car updateCar(Car car);

    void delete(int id);
}
