package pl.mikolaj.webservicetest.dao.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;
import pl.mikolaj.webservicetest.beans.Car;
import pl.mikolaj.webservicetest.dao.CarDao;
import pl.mikolaj.webservicetest.exceptions.ObjectNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDaoImpl implements CarDao {
    private static final String CARS_FILE_PATH = "src\\main\\resources\\cars.json";

    @Override
    public List<Car> getAllCars() {
        Type listType = new TypeToken<ArrayList<Car>>() {}.getType();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(CARS_FILE_PATH);
            List<Car> result = new Gson().fromJson(fileReader, listType);
            fileReader.close();
            return result;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Car findById(int id) {
        return getAllCars().stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(id));
    }

    @Override
    public Car saveCar(Car newCar) {
        Car existingCar = null;
        if(newCar.getId() != null) {
            existingCar = findById(newCar.getId());
        }

        if(existingCar == null) {
            return createNewCar(newCar);
        }else {
            return updateCar(newCar);
        }
    }

    @Override
    public void delete(int id) {
        List<Car> allCars = getAllCars();
        Car toBeDeleted = allCars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(id));

        allCars.remove(toBeDeleted);
        saveAllCars(allCars);
    }

    private Car createNewCar(Car newCar) {
        List<Car> allCars = getAllCars();
        int maxId = allCars
                .stream()
                .mapToInt(Car::getId)
                .max()
                .getAsInt();

        maxId++;
        newCar.setId(maxId);
        allCars.add(newCar);
        saveAllCars(allCars);
        return newCar;
    }

    private void saveAllCars(List<Car> allCars) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        FileWriter writer = null;

        try {
            writer = new FileWriter(CARS_FILE_PATH);
            gson.toJson(allCars, writer);
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private Car updateCar(Car newCar) {
        List<Car> allCars = getAllCars();
        Car existingCar = allCars.stream()
                .filter(c -> c.getId().equals(newCar.getId()))
                .findFirst()
                .get();

        existingCar.setMake(newCar.getMake());
        existingCar.setModel(newCar.getModel());
        existingCar.setColor(newCar.getColor());
        existingCar.setYear(newCar.getYear());
        existingCar.setPrice(newCar.getPrice());

        saveAllCars(allCars);
        return existingCar;
    }
}
