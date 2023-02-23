package pl.mikolaj.webservicetest.beans;

public class CarDTO {
    private int id;
    private String make;
    private String model;

    public CarDTO(int id, String make, String model) {
        this.id = id;
        this.make = make;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public static CarDTO map(Car car) {
        return new CarDTO(
                car.getId(),
                car.getMake(),
                car.getModel()
        );
    }
}
