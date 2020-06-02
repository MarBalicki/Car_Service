package car.service;

import car.service.model.Car;
import car.service.model.Mechanic;
import car.service.model.Owner;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarDao carDao = new CarDao();
        MechanicDao mechanicDao = new MechanicDao();
        OwnerDao ownerDao = new OwnerDao();
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Choose order: [addCar/addMechanic/updateCar/" +
                    "updateMechanic/carList/mechanicList/deleteCar/deleteMechanic/" +
                    "connectMechanic/findPhoneNumber/findLastName/selectCar/selectOwner/quit]");
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("addCar")) {
                carDao.addNewCar(scanner);
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
            } else if (command.equalsIgnoreCase("findPhoneNumber")) {
                findByPhoneNumber(scanner);
            } else if (command.equalsIgnoreCase("findLastName")) {
                findByLastName(scanner);
            } else if (command.equalsIgnoreCase("selectCar")) {
                selectCarBy(carDao, scanner);
            } else if (command.equalsIgnoreCase("selectOwner")) {
                selectOwnerBy(ownerDao, scanner);
            }
        } while (!command.equalsIgnoreCase("quit"));

    }

    private static void selectCarBy(CarDao dao, Scanner scanner) {
        System.out.println("Choose select by: [id/mechanicsOfThisCar]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("id")) {
            dao.findCarById(scanner);
        } else if (command.equalsIgnoreCase("mechanicsOfThisCar")) {
            dao.carListOfMechanics(scanner);
        } else {
            System.out.println("Wrong command!");
        }
    }

    private static void selectOwnerBy(OwnerDao dao, Scanner scanner) {
        System.out.println("Choose find by: [id/addCar/carList]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("id")) {
            dao.findOwnerById(scanner);
        } else if (command.equalsIgnoreCase("addCar")) {
            dao.addCarToOwner(scanner);
        } else if (command.equalsIgnoreCase("carList")) {
            dao.ownerCarList(scanner);
        } else {
            System.out.println("Wrong command!");
        }
    }

    private static void findByPhoneNumber(Scanner scanner) {
        System.out.println("Phone number You are looking for: ");
        try {
            int phoneNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Founded records: ");
            List<Owner> ownerList = new OwnerDao().ownerEntityDao.findByPhoneNumber(Owner.class, phoneNumber);
            List<Mechanic> mechanicList = new MechanicDao().mechanicEntityDao.findByPhoneNumber(Mechanic.class, phoneNumber);
            ownerList.forEach(System.out::println);
            mechanicList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Wrong data!");
        }
    }

    private static void findByLastName(Scanner scanner) {
        System.out.println("Last name You are looking: ");
        try {
            String lastName = scanner.nextLine();
            List<Owner> ownerList = new OwnerDao().ownerEntityDao.findByLastName(Owner.class, lastName);
            List<Mechanic> mechanicList = new MechanicDao().mechanicEntityDao.findByLastName(Mechanic.class, lastName);
            ownerList.forEach(System.out::println);
            mechanicList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Wrong data!");
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
