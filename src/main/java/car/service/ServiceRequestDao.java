package car.service;

import car.service.model.Car;
import car.service.model.ServiceRequest;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

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
        serviceRequestEntityDao.getAll(ServiceRequest.class).forEach(System.out::println);
    }

    protected void closeServiceRequest(Scanner scanner) {
        System.out.println("Which service request ID You want to close: ");
        try {
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
                        .car(serviceRequestOptional.get().getCar())
                        .id(serviceRequestID)
                        .build();
                serviceRequestEntityDao.saveOrUpdate(request);
            } else {
                System.err.println("Service request with that ID not exist!");
            }
        } catch (Exception e) {
            System.err.println("Wrong data!");
        }
    }

    protected List<ServiceRequest> stillOpenServiceRequests() {
        List<ServiceRequest> notFinishedList = new ArrayList<>();
        List<ServiceRequest> list = serviceRequestEntityDao.getAll(ServiceRequest.class);
        for (ServiceRequest request : list) {
//            int openServiceRequests = request.getOpenServiceRequests();
            if (request.getRepairDate() == null) {
                notFinishedList.add(request);
            }
        }
        return notFinishedList;
    }

    protected void closedServiceRequests() {
        int closedRequests = serviceRequestEntityDao.getAll(ServiceRequest.class).size()
                - stillOpenServiceRequests().size();
        System.out.printf("We have %d open service requests.\n", closedRequests);
    }

    protected void openServiceRequests() {
        System.out.printf("We have %d open service requests.\n", stillOpenServiceRequests().size());
    }

    protected void requestsTotalAmount() {
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Set<ServiceRequest> set = session.createQuery()
            ServiceRequest request = session.get(ServiceRequest.class, 1L);
            System.out.println("Total amount of all service requests is: "
                    + request.getTotalAmount());
        } catch (HibernateException he) {
            he.printStackTrace();
        }

    }
}
