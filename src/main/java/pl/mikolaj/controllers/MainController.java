package pl.mikolaj.controllers;

import org.springframework.web.bind.annotation.*;
import pl.mikolaj.beans.Car;
import pl.mikolaj.beans.CarDTO;
import pl.mikolaj.beans.Person;
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
    public List<CarDTO> cars() {
        return carService.getAllCars()
                .stream()
                .map(CarDTO::map)
                .toList();
    }

    @GetMapping("/cars/{id}")
    public CarDTO findCarById(@PathVariable int id) {
        return carService.findById(id)
                .toCarDTO();
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
                .map(Person::toPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/people/{id}")
    public PersonDTO findPersonById(@PathVariable int id) {
        return personService.findById(id)
                .toPersonDTO();
    }
}
