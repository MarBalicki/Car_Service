package car.service;

import car.service.model.Car;
import car.service.model.Owner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;

public class OwnerDao {
    EntityDao<Owner> ownerEntityDao = new EntityDao<>();
    private final static String OWNER_NOT_EXIST = "Owner with that ID not exist!";

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
        System.out.println("Owner ID: ");
        try {
            Long ownerID = Long.parseLong(scanner.nextLine());
            addCarToOwner(ownerID, scanner);
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    private void addCarToOwner(Long ownerID, Scanner scanner) {
        Optional<Owner> ownerOptional = ownerEntityDao.findById(Owner.class, ownerID);
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            String[] data = new CarDao().dataLine(scanner);
            Car car = new CarDao().buildCar(data);
            car.setOwner(owner);
            new EntityDao<Car>().saveOrUpdate(car);
        } else {
            System.err.println(OWNER_NOT_EXIST);
        }
    }

    protected void findOwnerById(Scanner scanner) {
        System.out.println("Owner ID: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Owner> ownerOptional = ownerEntityDao.findById(Owner.class, id);
            if (ownerOptional.isPresent()) {
                Owner owner = ownerOptional.get();
                System.out.println(owner);
            } else {
                System.err.println(OWNER_NOT_EXIST);
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }


}
