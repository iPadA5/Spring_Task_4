package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        userService.add(new User("User5", "Lastname5", "user5@mail.ru",
                new Car("Porcshe", 911)));
        userService.add(new User("User6", "Lastname6", "user6@mail.ru",
                new Car("Lada Granta", 21900)));
        userService.add(new User("User7", "Lastname7", "user7@mail.ru",
                new Car("Mercedes", 930)));
        userService.add(new User("User8", "Lastname8", "user8@mail.ru",
                new Car("Porsche", 911)));

        List<User> users = userService.listUsers();
        printUsersFromList(users);
        List<User> userWithCar = userService.getUserByCar("Porsche", 911);
        printUsersFromList(userWithCar);

        context.close();
    }

    private static void printUsersFromList(List<User> users) {
        for (User user : users) {
            System.out.println("First name = " + user.getFirstName());
            System.out.println("Last name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
        }
    }
}
