package pl.mikolaj.services;

import pl.mikolaj.beans.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    Car findById(int id);

    Car createCar(Car car);

    Car updateCar(Car car);

    void delete(int id);
}
