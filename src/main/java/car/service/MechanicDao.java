package car.service;

import car.service.model.Mechanic;

import java.util.Optional;
import java.util.Scanner;

public class MechanicDao {
    EntityDao<Mechanic> mechanicEntityDao = new EntityDao<>();

    protected void addMechanic(Scanner scanner) {
        System.out.println("Give data separated by a space: [NAME/LAST_NAME/PHONE_NUMBER]");
        String line = scanner.nextLine();
        String[] data = line.split(" ");
        try {
            Mechanic mechanic = Mechanic.builder()
                    .name(data[0].substring(0, 1).toUpperCase()
                            + data[0].substring(1).toLowerCase())
                    .lastName(data[1].substring(0, 1).toUpperCase()
                            + data[1].substring(1).toLowerCase())
                    .phoneNumber(Integer.parseInt(data[2]))
                    .build();
            mechanicEntityDao.saveOrUpdate(mechanic);
        } catch (Exception e) {
            System.err.println("Wrong data! HAVE TO BE PHONE NUMBER!");
        }
    }

    protected void updateMechanic(Scanner scanner) {
        System.out.println("Which ID You want to change: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            Optional<Mechanic> optionalMechanic = mechanicEntityDao.findById(Mechanic.class, id);
            if (optionalMechanic.isPresent()) {
                System.out.println("Give data separated by a space: [NAME/LAST_NAME/STILL_WORK]");
                String line = scanner.nextLine();
                String[] data = line.split(" ");
                Mechanic mechanic = Mechanic.builder()
                        .name(data[0].substring(0, 1).toUpperCase()
                                + data[0].substring(1).toLowerCase())
                        .lastName(data[1].substring(0, 1).toUpperCase()
                                + data[1].substring(1).toLowerCase())
                        .phoneNumber(Integer.parseInt(data[2]))
                        .stillWork(Boolean.parseBoolean(data[2]))
                        .id(id)
                        .build();
                mechanicEntityDao.saveOrUpdate(mechanic);
            } else {
                System.err.println("Mechanic with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    protected void showAllMechanic() {
        System.out.println("List of all mechanics: ");
        mechanicEntityDao.getAll(Mechanic.class).forEach(System.out::println);
    }

    protected void deleteMechanic(Scanner scanner) {
        System.out.println("Which ID You want to delete: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            Optional<Mechanic> optionalMechanic = mechanicEntityDao.findById(Mechanic.class, id);
            if (optionalMechanic.isPresent()) {
                Mechanic mechanic = optionalMechanic.get();
                mechanicEntityDao.delete(mechanic);
            } else {
                System.err.println("Mechanic with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }
}
