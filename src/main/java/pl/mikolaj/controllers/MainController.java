package pl.mikolaj.controllers;

import org.springframework.web.bind.annotation.*;
import pl.mikolaj.beans.Car;
import pl.mikolaj.beans.PersonDTO;
import pl.mikolaj.services.CarService;
import pl.mikolaj.services.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    CarService carService;
    PersonService personService;

    public MainController(CarService carService,
                          PersonService personService) {
        this.carService = carService;
        this.personService = personService;
    }

    @GetMapping("/cars")
    public List<Car> cars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public Car findById(@PathVariable int id) {
        return carService.findById(id);
    }

    @PostMapping("/cars")
    public Car createCar(@RequestBody Car newCar) {
        return carService.createCar(newCar);
    }

    @PutMapping("/cars/{id}")
    public Car updateCar(@RequestBody Car car, @PathVariable int id) {
        car.setId(id);
        return carService.updateCar(car);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable int id) {
        carService.delete(id);
    }

    @GetMapping("/people")
    public List<PersonDTO> people() {
        return personService.getAllPeople()
                .stream()
                .map(PersonDTO::map)
                .collect(Collectors.toList());
    }
}
