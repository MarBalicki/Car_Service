package car.service;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarDao dao = new CarDao();
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Choose order: [addCar/quit]");
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("addCar")) {
                dao.addCar(scanner);
            }

        } while (command.equalsIgnoreCase("quit"));


    }
}
