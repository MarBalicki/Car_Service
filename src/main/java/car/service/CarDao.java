package car.service;

import car.service.model.BodyStyle;
import car.service.model.Brand;
import car.service.model.Car;
import car.service.model.EngineType;

import java.util.Scanner;

public class CarDao {
    EntityDao<Car> entityDao = new EntityDao<>();

    protected void addCar(Scanner scanner) {
        System.out.println("Give data separated by a space: " +
                "[REGISTRATION_NUMBER/BRAND/MODEL/BODY_STYLE/PRODUCTION_YEAR/CAPACITY/ENGINE_TYPE]");
        String line = scanner.nextLine();
        String[] data = line.split(" ");
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
        } catch (
                Exception e) {
            System.out.println("Wrong data!");
        }
    }


}
