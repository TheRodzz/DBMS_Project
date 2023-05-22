public class User {
    private int uid;
    private String name;
    private String user_name;
    private String password;
    private Boolean isAdmin;

    /**
     * Constructor for the User class.
     *
     * @param uid       The ID of the user.
     * @param name      The name of the user.
     * @param user_name The username of the user.
     * @param password  The password of the user.
     * @param isAdmin   A boolean indicating if the user is an admin or not.
     */
    public User(int uid, String name, String user_name, String password, Boolean isAdmin) {
        this.uid = uid;
        this.name = name;
        this.user_name = user_name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Returns the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getUid() {
        return this.uid;
    }

    /**
     * Sets the ID of the user.
     *
     * @param uid The ID of the user.
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    public String getUser_name() {
        return this.user_name;
    }

    /**
     * Sets the username of the user.
     *
     * @param user_name The username of the user.
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a boolean indicating if the user is an admin or not.
     */

    public Boolean isIsAdmin() {
        return this.isAdmin;
    }

    /**
     * Returns a boolean indicating if the user is an admin or not.
     *
     * @return true if the user is an admin, false otherwise.
     */
    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    /**
     * Sets whether the user is an admin or not.
     *
     * @param isAdmin A boolean indicating if the user is an admin.
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "{" +
                " uid='" + getUid() + "'" +
                ", name='" + getName() + "'" +
                ", user_name='" + getUser_name() + "'" +
                ", password='" + getPassword() + "'" +
                ", isAdmin='" + isIsAdmin() + "'" +
                "}";
    }

}
