import java.util.Scanner;

public class InputHandler {
    static Scanner sc = new Scanner(System.in);

    /**
     * Reads and returns the input for creating a new Media object.
     *
     * @return A new Media object with the entered details.
     */
    public static Media getMediaInput() {

        System.out.println("Enter the media duration:");
        int duration = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        System.out.println("Enter the media title:");
        String title = sc.nextLine();

        System.out.println("Enter the media release date in yyyy-MM-dd format:");
        String releaseDate = sc.nextLine();

        System.out.println("Enter the media poster URL:");
        String posterUrl = sc.nextLine();

        System.out.println("Enter the media trailer URL:");
        String trailerUrl = sc.nextLine();

        return new Media(0, duration, title, releaseDate, posterUrl, trailerUrl);
    }

    /**
     * Reads and confirms a new password input from the user.
     *
     * @return The confirmed password.
     */
    public static String pwdInputAndConfirm() {
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

    /**
     * Reads and returns the user's rating input.
     *
     * @return The user's rating.
     */
    public static double getRatingInput() {
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

    /**
     * Reads and returns the user's ID input.
     *
     * @return The user's ID.
     */
    public static int getIDinput() {
        int id = sc.nextInt();
        sc.nextLine(); // consume new line character
        return id;
    }

    /**
     * Reads and returns a non-empty string input from the user.
     *
     * @return The non-empty string input.
     */
    public static String getNonEmptyString() {
        while (true) {

            String inp = sc.nextLine();
            if (inp.isEmpty()) {
                System.out.println("You did not input anything. Try again");
            } else
                return inp;
        }
    }

    /**
     * Reads and returns a string input from the user.
     *
     * @return The string input.
     */
    public static String getStringInput() {
        return sc.nextLine();
    }

    /**
     * Updates the provided Media object with new details entered by the user.
     *
     * @param media The Media object to be updated.
     * @return The updated Media object.
     */
    public static Media updateMedia(Media media) {
        System.out.println("Enter the new title (or press Enter to keep the current value):");
        String newTitle = sc.nextLine();
        if (!newTitle.isEmpty()) {
            media.setTitle(newTitle);
        }

        System.out.println(
                "Enter the new release date in the format (yyyy-MM-dd) (or press Enter to keep the current value):");
        String newReleaseDateStr = sc.nextLine();
        if (!newReleaseDateStr.isEmpty()) {
            media.setRelease_date(newReleaseDateStr);
        }

        System.out.println("Enter the new duration in minutes (or press Enter to keep the current value):");
        String newDurationStr = sc.nextLine();
        if (!newDurationStr.isEmpty()) {
            int newDuration = Integer.parseInt(newDurationStr);
            media.setDuration(newDuration);
        }

        System.out.println("Enter the new poster URL (or press Enter to keep the current value):");
        String newPosterUrl = sc.nextLine();
        if (!newPosterUrl.isEmpty()) {
            media.setPoster_url(newPosterUrl);
        }

        System.out.println("Enter the new trailer URL (or press Enter to keep the current value):");
        String newTrailerUrl = sc.nextLine();
        if (!newTrailerUrl.isEmpty()) {
            media.setTrailer_url(newTrailerUrl);
        }
        return media;

    }
}
