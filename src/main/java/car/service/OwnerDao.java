package car.service;

import car.service.model.Car;
import car.service.model.Owner;

import java.util.Optional;
import java.util.Scanner;

public class OwnerDao {
//    CarDao carDao = new CarDao();

    protected Owner buildOwner(Scanner scanner) {
        System.out.println("Add owner to car: [NAME/LAST_NAME/PHONE_NUMBER]");
        String ownerLine = scanner.nextLine();
        String[] ownerData = ownerLine.split(" ");
        return Owner.builder()
                .name(ownerData[0].substring(0, 1).toUpperCase()
                        + ownerData[0].substring(1).toLowerCase())
                .lastName(ownerData[1].substring(0, 1).toUpperCase()
                        + ownerData[1].substring(1).toLowerCase())
                .phoneNumber(Integer.parseInt(ownerData[2]))
                .build();
    }

    protected void addCarToOwner(Scanner scanner) {
        System.out.println("Give Owner ID: ");
        try {
            Long ownerID = Long.parseLong(scanner.nextLine());
            addCarToOwner(ownerID, scanner);
        } catch (Exception e) {
            System.out.println("Wrong data!");
        }
    }

    private void addCarToOwner(Long ownerID, Scanner scanner) {
        Optional<Owner> ownerOptional = new EntityDao<Owner>().findById(Owner.class, ownerID);
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            String[] data = new CarDao().dataLine(scanner);
            Car car = new CarDao().buildCar(data);
            new EntityDao<Car>().saveOrUpdate(car);
            owner.getCarSet().add(car);
            new EntityDao<Owner>().saveOrUpdate(owner);
        } else {
            System.out.println("Owner with that ID not exist!");
        }
    }

    protected void addCarToOwner(Long ownerID, Long carID) {
        Optional<Owner> ownerOptional = new EntityDao<Owner>().findById(Owner.class, ownerID);
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            Optional<Car> carOptional = new EntityDao<Car>().findById(Car.class, carID);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                owner.getCarSet().add(car);
                new EntityDao<Owner>().saveOrUpdate(owner);
            } else {
                System.out.println("Car with that ID not exist!");
            }
        } else {
            System.out.println("Owner with that ID not exist!");
        }
    }


}
