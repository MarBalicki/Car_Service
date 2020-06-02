package car.service;

import car.service.model.Car;
import car.service.model.Owner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

public class OwnerDao {
    //    CarDao carDao = new CarDao();
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
            new EntityDao<Car>().saveOrUpdate(car);
            owner.getCarSet().add(car);
            new EntityDao<Owner>().saveOrUpdate(owner);
        } else {
            System.err.println(OWNER_NOT_EXIST);
        }
    }

    protected void addCarToOwner(Long ownerID, Long carID) {
        Optional<Owner> ownerOptional = ownerEntityDao.findById(Owner.class, ownerID);
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            Optional<Car> carOptional = new EntityDao<Car>().findById(Car.class, carID);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                owner.getCarSet().add(car);
                new EntityDao<Owner>().saveOrUpdate(owner);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } else {
            System.err.println(OWNER_NOT_EXIST);
        }
    }

    public void findOwnerById(Scanner scanner) {
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

    public void ownerCarList(Scanner scanner) {
        System.out.println("Owner ID: ");
        Long ownerID = Long.parseLong(scanner.nextLine());
        Optional<Owner> ownerOptional = ownerEntityDao.findById(Owner.class, ownerID);
        if (ownerOptional.isPresent()) {
            findOwnerCars(ownerID).forEach(System.out::println);
        } else {
            System.err.println(OWNER_NOT_EXIST);
        }
    }

    private Set<Car> findOwnerCars(Long ownerID) {
        Set<Car> carSet = new HashSet<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Owner owner = session.get(Owner.class, ownerID);
            carSet.addAll(owner.getCarSet());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return carSet;
    }


}
