import java.util.Scanner;

public class InputHandler {

    public static Media getMediaInput(Scanner scanner) {

        System.out.println("Enter the media duration:");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter the media title:");
        String title = scanner.nextLine();

        System.out.println("Enter the media release date in yyyy-MM-dd format:");
        String releaseDate = scanner.nextLine();

        System.out.println("Enter the media poster URL:");
        String posterUrl = scanner.nextLine();

        System.out.println("Enter the media trailer URL:");
        String trailerUrl = scanner.nextLine();

        return new Media(0, duration, title, releaseDate, posterUrl, trailerUrl);
    }

    public static String pwdInputAndConfirm(Scanner sc) {
        String pass, pass1;
        while (true) {
            System.out.println("Enter new password");
            pass = sc.nextLine();
            System.out.println("Re-enter password");
            pass1 = sc.nextLine();
            if (!pass.equals(pass1)) {
                System.out.println("Password mismatch. Try again");
            } else
                break;
        }
        return pass;
    }

    public static double getRatingInput(Scanner sc) {
        double rating = 0;
        while (true) {
            System.out.println("Enter rating");
            rating = sc.nextDouble();
            sc.nextLine(); // consume newline char
            if (rating >= 0 && rating <= 5)
                return rating;
            else {
                System.out.println("Invalid input. Rating must be between 0 and 5.");
                System.out.println("Try Again");
            }
        }
    }

    public static int getIDinput(Scanner sc) {
        int id = sc.nextInt();
        sc.nextLine(); // consume new line character
        return id;
    }

    public static String getNonEmptyString(Scanner sc) {
        while (true) {

            String inp = sc.nextLine();
            if (inp.isEmpty()) {
                System.out.println("You did not input anything. Try again");
            } else
                return inp;
        }
    }

    public static String getStringInput(Scanner sc) {
        return sc.nextLine();
    }

    public static Media updateMedia(Scanner scanner, Media media) {
        System.out.println("Enter the new title (or press Enter to keep the current value):");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            media.setTitle(newTitle);
        }

        System.out.println(
                "Enter the new release date in the format (yyyy-MM-dd) (or press Enter to keep the current value):");
        String newReleaseDateStr = scanner.nextLine();
        if (!newReleaseDateStr.isEmpty()) {
            media.setRelease_date(newReleaseDateStr);
        }

        System.out.println("Enter the new duration in minutes (or press Enter to keep the current value):");
        String newDurationStr = scanner.nextLine();
        if (!newDurationStr.isEmpty()) {
            int newDuration = Integer.parseInt(newDurationStr);
            media.setDuration(newDuration);
        }

        System.out.println("Enter the new poster URL (or press Enter to keep the current value):");
        String newPosterUrl = scanner.nextLine();
        if (!newPosterUrl.isEmpty()) {
            media.setPoster_url(newPosterUrl);
        }

        System.out.println("Enter the new trailer URL (or press Enter to keep the current value):");
        String newTrailerUrl = scanner.nextLine();
        if (!newTrailerUrl.isEmpty()) {
            media.setTrailer_url(newTrailerUrl);
        }
        return media;

    }
}
