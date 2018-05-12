package Model;

public class User {

    private int userID, phone;
    private String username, firstname, lastname, email, role, password;

    public User(int userID, int phone, String username, String firstname, String lastname, String email, String role, String password) {
        this.userID = userID;
        this.phone = phone;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public int getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
