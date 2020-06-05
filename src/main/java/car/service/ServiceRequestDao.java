package car.service;

import car.service.model.Car;
import car.service.model.ServiceRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class ServiceRequestDao {
    EntityDao<ServiceRequest> serviceRequestEntityDao = new EntityDao<>();
    EntityDao<Car> carEntityDao = new EntityDao<>();

    protected void addRequestToCar(Scanner scanner) {
        System.out.println("Car ID to add service request: ");
        try {
            Long carID = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = carEntityDao.findById(Car.class, carID);
            if (carOptional.isPresent()) {
                System.out.println("Put description: ");
                String description = scanner.nextLine();
                ServiceRequest serviceRequest = ServiceRequest.builder()
                        .createdRequestDate(LocalDateTime.now().withNano(0))
                        .description(description)
                        .build();
                Car car = carOptional.get();
                serviceRequest.setCar(car);
                serviceRequestEntityDao.saveOrUpdate(serviceRequest);
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    protected void showAllRequest() {
        System.out.println("List of all service requests: ");
        serviceRequestEntityDao.showAll(ServiceRequest.class).forEach(System.out::println);
    }

    protected void closeServiceRequest(Scanner scanner) {
        System.out.println("Write car ID: ");
        try {
            Long carID = Long.parseLong(scanner.nextLine());
            Optional<Car> carOptional = carEntityDao.findById(Car.class, carID);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
//                Set<ServiceRequest> set = car.getServiceRequestSet();
                car.getServiceRequestSet().forEach(System.out::println);
                System.out.println("Which service request ID You want to close: ");
                Long serviceRequestID = Long.parseLong(scanner.nextLine());
                Optional<ServiceRequest> serviceRequestOptional
                        = serviceRequestEntityDao.findById(ServiceRequest.class, serviceRequestID);
                if (serviceRequestOptional.isPresent()) {
                    System.out.println("Put total amount of repair: ");
                    String data = scanner.nextLine();
                    ServiceRequest request = ServiceRequest.builder()
                            .description(serviceRequestOptional.get().getDescription())
                            .amount(Double.parseDouble(data))
                            .createdRequestDate(serviceRequestOptional.get().getCreatedRequestDate())
                            .repairDate(LocalDateTime.now())
                            .car(car)
                            .id(serviceRequestID)
                            .build();
                    serviceRequestEntityDao.saveOrUpdate(request);
                } else {
                    System.err.println("Service request with that ID not exist!");
                }
            } else {
                System.err.println("Car with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }
}
