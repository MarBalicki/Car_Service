package car.service;

import car.service.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class CarDao {
    EntityDao<Car> carEntityDao = new EntityDao<>();
    OwnerDao ownerDao = new OwnerDao();
    EntityDao<Owner> ownerEntityDao = new EntityDao<>();

    protected void addNewCar(Scanner scanner) {
        String[] data = dataLine(scanner);
        Car car = null;
        Owner owner = null;
        try {
            car = buildCar(data);
            carEntityDao.saveOrUpdate(car);
            owner = ownerDao.buildOwner(scanner);
            ownerEntityDao.saveOrUpdate(owner);
            Long carID = car.getId();
            Long ownerID = owner.getId();
            ownerDao.addCarToOwner(ownerID, carID);
        } catch (Exception e) {
            if (car != null) {
                carEntityDao.delete(car);
            }
            if (owner != null) {
                ownerEntityDao.delete(owner);
            }
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
        System.out.println("Which id You want to delete: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = carEntityDao.findById(Car.class, id);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                carEntityDao.delete(car);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");

        }
    }

    protected void showAllCars() {
        System.out.println("List of all cars: ");
        carEntityDao.showAll(Car.class).forEach(System.out::println);
    }

    protected void carListOfMechanics(Scanner scanner) {
        System.out.println("Write car ID: ");
        try {
            Long carID = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = carEntityDao.findById(Car.class, carID);
            if (carOptional.isPresent()) {
                findMechanics(carID).forEach(System.out::println);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    private Set<Mechanic> findMechanics(Long id) {
        Set<Mechanic> mechanicSet = new HashSet<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Car car = session.get(Car.class, id);
            mechanicSet.addAll(car.getMechanicSet());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return mechanicSet;
    }

    public void findCarById(Scanner scanner) {
        System.out.println("What ID You are looking for: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = new EntityDao<Car>().findById(Car.class, id);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                System.out.println(car);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

}
