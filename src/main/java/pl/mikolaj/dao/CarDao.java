package pl.mikolaj.dao;

import pl.mikolaj.beans.Car;

import java.util.List;

public interface CarDao {
    List<Car> getAllCars();

    Car findById(int id);

    Car saveCar(Car car);

    void delete(int id);
}