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
    EntityDao<Car> entityDao = new EntityDao<>();

    protected void addCar(Scanner scanner) {
        String[] data = dataLine(scanner);
        try {
            Car car = Car.builder()
                    .registrationNumber(data[0].toUpperCase())
                    .brand(Brand.valueOf(data[1].toUpperCase()))
                    .model(data[2].toUpperCase())
                    .bodyStyle(BodyStyle.valueOf(data[3].toUpperCase()))
                    .productionYear(Integer.parseInt(data[4]))
                    .capacity(Double.parseDouble(data[5]))
                    .engineType(EngineType.valueOf(data[6].toUpperCase()))
                    .build();
            entityDao.saveOrUpdate(car);
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    protected void updateCar(Scanner scanner) {
        System.out.println("Which id You want to change: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = entityDao.findById(Car.class, id);
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
                entityDao.saveOrUpdate(car);

            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    private String[] dataLine(Scanner scanner) {
        System.out.println("Give data separated by a space: " +
                "[REGISTRATION_NUMBER/BRAND/MODEL/BODY_STYLE/PRODUCTION_YEAR/CAPACITY/ENGINE_TYPE]");
        String line = scanner.nextLine();
        return line.split(" ");
    }

    protected void deleteCar(Scanner scanner) {
        System.out.println("Which id You want to delete: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = entityDao.findById(Car.class, id);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                entityDao.delete(car);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");

        }
    }

    protected void showAllCars() {
        System.out.println("List of all cars: ");
        entityDao.showAll(Car.class).forEach(System.out::println);
    }

    protected void carListOfMechanics(Scanner scanner) {
        System.out.println("Write car ID: ");
        try {
            Long carID = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = entityDao.findById(Car.class, carID);
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
}
