package car.service;

import car.service.model.*;

import java.util.Optional;
import java.util.Scanner;

public class CarDao {
    EntityDao<Car> carEntityDao = new EntityDao<>();
    OwnerDao ownerDao = new OwnerDao();
    EntityDao<Owner> ownerEntityDao = new EntityDao<>();

    protected void addNewCar(Scanner scanner) {
        String[] data = dataLine(scanner);
        Car car;
        Owner owner;
        try {
            car = buildCar(data);
            owner = ownerDao.buildOwner(scanner);
            ownerEntityDao.saveOrUpdate(owner);
            car.setOwner(owner);
            carEntityDao.saveOrUpdate(car);
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    protected Car buildCar(String[] data) {
        return Car.builder()
                .registrationNumber(data[0].toUpperCase())
                .brand(Brand.valueOf(data[1].toUpperCase()))
                .model(data[2].toUpperCase())
                .bodyStyle(BodyStyle.valueOf(data[3].toUpperCase()))
                .productionYear(Integer.parseInt(data[4]))
                .capacity(Double.parseDouble(data[5]))
                .engineType(EngineType.valueOf(data[6].toUpperCase()))
                .build();
    }

    protected void updateCar(Scanner scanner) {
        System.out.println("Which id You want to change: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = carEntityDao.findById(Car.class, id);
            if (carOptional.isPresent()) {
                String[] data = dataLine(scanner);
                Car car = Car.builder()
                        .registrationNumber(data[0].toUpperCase())
                        .brand(Brand.valueOf(data[1].toUpperCase()))
                        .model(data[2].toUpperCase())
                        .bodyStyle(BodyStyle.valueOf(data[3].toUpperCase()))
                        .productionYear(Integer.parseInt(data[4]))
                        .capacity(Double.parseDouble(data[5]))
                        .engineType(EngineType.valueOf(data[6].toUpperCase()))
                        .id(id)
                        .build();
                carEntityDao.saveOrUpdate(car);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    protected String[] dataLine(Scanner scanner) {
        System.out.println("Give data separated by a space: " +
                "[REGISTRATION_NUMBER/BRAND/MODEL/BODY_STYLE/PRODUCTION_YEAR/CAPACITY/ENGINE_TYPE]");
        String line = scanner.nextLine();
        return line.split(" ");
    }

    protected void deleteCar(Scanner scanner) {
        Car car = getCar(scanner);
        carEntityDao.delete(car);
    }

    protected void showAllCars() {
        System.out.println("List of all cars: ");
        carEntityDao.showAll(Car.class).forEach(System.out::println);
    }

    protected void carListOfMechanics(Scanner scanner) {
        Car car = getCar(scanner);
        if (car != null) {
            car.getMechanicSet().forEach(System.out::println);
        }
    }

    protected void findCarById(Scanner scanner) {
        Car car = getCar(scanner);
        System.out.println(car);
    }

    protected void findOwner(Scanner scanner) {
        Car car = getCar(scanner);
        Owner owner = null;
        if (car != null) {
            owner = car.getOwner();
        }
        System.out.println(owner);
    }

    protected void serviceRequestsOfCar(Scanner scanner) {
        Car car = getCar(scanner);
        if (car != null) {
            car.getServiceRequestSet().forEach(System.out::println);
        }
    }

    private Car getCar(Scanner scanner) {
        System.out.println("Write car ID: ");
        try {
            Long carID = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = carEntityDao.findById(Car.class, carID);
            if (carOptional.isPresent()) {
                return carOptional.get();
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
        return null;
    }
}
