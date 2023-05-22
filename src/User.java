public class User {
    private int uid;
    private String name;
    private String user_name;
    private String password;
    private Boolean isAdmin;

    public User(int uid, String name, String user_name, String password, Boolean isAdmin) {
        this.uid = uid;
        this.name = name;
        this.user_name = user_name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isIsAdmin() {
        return this.isAdmin;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;

    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

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
