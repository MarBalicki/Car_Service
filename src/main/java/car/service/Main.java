package car.service;

import car.service.model.Car;
import car.service.model.Mechanic;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarDao carDao = new CarDao();
        MechanicDao mechanicDao = new MechanicDao();
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Choose order: [addCar/addMechanic/updateCar/updateMechanic/carList" +
                    "/mechanicList/deleteCar/deleteMechanic/connectMechanic/selectCar/quit]");
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("addCar")) {
                carDao.addCar(scanner);
            } else if (command.equalsIgnoreCase("addMechanic")) {
                mechanicDao.addMechanic(scanner);
            } else if (command.equalsIgnoreCase("updateCar")) {
                carDao.updateCar(scanner);
            } else if (command.equalsIgnoreCase("updateMechanic")) {
                mechanicDao.updateMechanic(scanner);
            } else if (command.equalsIgnoreCase("carList")) {
                carDao.showAllCars();
            } else if (command.equalsIgnoreCase("mechanicList")) {
                mechanicDao.showAllMechanic();
            } else if (command.equalsIgnoreCase("deleteCar")) {
                carDao.deleteCar(scanner);
            } else if (command.equalsIgnoreCase("deleteMechanic")) {
                mechanicDao.deleteMechanic(scanner);
            } else if (command.equalsIgnoreCase("connectMechanic")) {
                connectMechanic(scanner);
            } else if (command.equalsIgnoreCase("selectCar")) {
                selectCarBy(carDao, scanner);
            }
        } while (!command.equalsIgnoreCase("quit"));

    }

    private static void selectCarBy(CarDao dao, Scanner scanner) {
        System.out.println("Choose select by: [id/mechanicsOfThisCar]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("mechanicsOfThisCar")) {
            dao.carListOfMechanics(scanner);
        }

    }

    private static void connectMechanic(Scanner scanner) {
        System.out.println("Pick car ID: ");
        Long carID = Long.valueOf(scanner.nextLine());
        System.out.println("Pick mechanic ID: ");
        Long mechanicID = Long.valueOf(scanner.nextLine());
        Optional<Car> carOptional = new EntityDao<Car>().findById(Car.class, carID);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            Optional<Mechanic> mechanicOptional = new EntityDao<Mechanic>().findById(Mechanic.class, mechanicID);
            if (mechanicOptional.isPresent()) {
                Mechanic mechanic = mechanicOptional.get();
                car.getMechanicSet().add(mechanic);
                new EntityDao<Car>().saveOrUpdate(car);
            } else {
                System.err.println("Mechanic with that ID not exist!");
            }
        } else {
            System.err.println("Car with that ID not exist!");
        }
    }


}
