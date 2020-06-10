package car.service;

import car.service.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarDao carDao = new CarDao();
        MechanicDao mechanicDao = new MechanicDao();
        OwnerDao ownerDao = new OwnerDao();
        ServiceRequestDao serviceRequestDao = new ServiceRequestDao();
        Scanner scanner = new Scanner(System.in);
        String command;

//        Car car = Car.builder()
//                .registrationNumber("dwr0851c")
//                .brand(Brand.FORD)
//                .model("mondeoiv")
//                .bodyStyle(BodyStyle.COMBI)
//                .productionYear(2014)
//                .capacity(2.0)
//                .engineType(EngineType.DIESEL)
//                .build();
//        Owner owner = Owner.builder()
//                .name("marcin")
//                .lastName("balicki")
//                .phoneNumber(501433659)
//                .build();
//        new EntityDao<Car>().saveOrUpdate(car);
//        new EntityDao<Owner>().saveOrUpdate(owner);
//        ownerDao.addCarToOwner(1L, 1L);

        do {
            System.out.println("Choose order: [addNewCar/addMechanic/addServiceRequest/" +
                    "carList/mechanicList/serviceRequestsList/connectMechanic/findPhoneNumber/" +
                    "findLastName/\nselectCar/selectOwner/selectRequest/quit]");
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("addNewCar")) {
                carDao.addNewCar(scanner);
            } else if (command.equalsIgnoreCase("addMechanic")) {
                mechanicDao.addMechanic(scanner);
            } else if (command.equalsIgnoreCase("addServiceRequest")) {
                serviceRequestDao.addRequestToCar(scanner);
            } else if (command.equalsIgnoreCase("carList")) {
                carDao.showAllCars();
            } else if (command.equalsIgnoreCase("mechanicList")) {
                mechanicDao.showAllMechanic();
            } else if (command.equalsIgnoreCase("serviceRequestsList")) {
                serviceRequestDao.showAllRequest();
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
            } else if (command.equalsIgnoreCase("selectMechanic")) {
                selectMechanicBy(mechanicDao, scanner);
            } else if (command.equalsIgnoreCase("selectRequest")) {
                selectRequestBy(serviceRequestDao, scanner);
            }
        } while (!command.equalsIgnoreCase("quit"));

    }

    private static void selectCarBy(CarDao dao, Scanner scanner) {
        System.out.println("Choose select by: " +
                "[id/findOwner/mechanicsOfThisCar/serviceRequests/closeServiceRequest" +
                "/notFinishedServiceRequest/updateCar/deleteCar]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("id")) {
            dao.findCarById(scanner);
        } else if (command.equalsIgnoreCase("findOwner")) {
            dao.findOwner(scanner);
        } else if (command.equalsIgnoreCase("mechanicsOfThisCar")) {
            dao.carListOfMechanics(scanner);
        } else if (command.equalsIgnoreCase("serviceRequests")) {
            dao.serviceRequestsOfCar(scanner);
        } else if (command.equalsIgnoreCase("updateCar")) {
            dao.updateCar(scanner);
        } else if (command.equalsIgnoreCase("deleteCar")) {
            dao.deleteCar(scanner);
        } else {
            System.out.println("Wrong command!");
        }
    }

    private static void selectOwnerBy(OwnerDao dao, Scanner scanner) {
        System.out.println("Choose select by: [id/addCar]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("id")) {
            dao.findOwnerById(scanner);
        } else if (command.equalsIgnoreCase("addCar")) {
            dao.addCarToOwner(scanner);
        } else {
            System.out.println("Wrong command!");
        }
    }

    private static void selectMechanicBy(MechanicDao mechanicDao, Scanner scanner) {
        System.out.println("Choose select by: [updateMechanic/deleteMechanic]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("updateMechanic")) {
            mechanicDao.updateMechanic(scanner);
        } else if (command.equalsIgnoreCase("deleteMechanic")) {
            mechanicDao.deleteMechanic(scanner);
        }
    }

    private static void selectRequestBy(ServiceRequestDao serviceRequestDao, Scanner scanner) {
        System.out.println("Choose select by: " +
                "[howManyOpen/howManyClosed/stillOpenRequests/requestsTotalAmount/closeServiceRequest]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("howManyClosed")) {
            serviceRequestDao.closedServiceRequests();
        } else if (command.equalsIgnoreCase("howManyOpen")) {
            serviceRequestDao.openServiceRequests();
        } else if (command.equalsIgnoreCase("stillOpenRequests")) {
            serviceRequestDao.stillOpenServiceRequests().forEach(System.out::println);
        } else if (command.equalsIgnoreCase("requestsTotalAmount")) {
            serviceRequestDao.requestsTotalAmount();
        } else if (command.equalsIgnoreCase("closeServiceRequest")) {
            serviceRequestDao.closeServiceRequest(scanner);
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
            System.err.println("Wrong data!");
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
            System.err.println("Wrong data!");
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
