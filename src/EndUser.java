import java.sql.*;
import java.util.Scanner;
public class EndUser implements UserInterface {
    User user;
    EndUser(User user){
        this.user=user;
    }
    @Override
    public void run(Scanner sc) {
        while (true) {
            System.out.println("Select your choice");
            System.out.println("1. List the available media");
            int choice = sc.nextInt();
            sc.nextLine(); // consume new line
            if (choice == 1) {
                this.listMedia();
            }
            
        }   
    }

    // view media, view history, update profile
}