package it.unicam.cs.ids.backend.model;

import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User {
    private final int id;
    private final String name;
    private final String surname;
    private final String address;
    private final String email;
    private final long telephone;
    private final String username;
    private final String password;

    /**
     * Constructs a user with the specified details.
     *
     * @param name      the name of the user
     * @param surname   the surname of the user
     * @param address   the address of the user
     * @param email     the email of the user
     * @param username  the username of the user
     * @param password  the password of the user
     * @param telephone the telephone number of the user
     */
    public User(String name, String surname, String address, String email, String username, String password, long telephone) {
        this.id = randomInt();
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
    }

    /**
     * Constructs a user with the specified details and ID.
     *
     * @param id        the ID of the user
     * @param name      the name of the user
     * @param surname   the surname of the user
     * @param address   the address of the user
     * @param email     the email of the user
     * @param username  the username of the user
     * @param password  the password of the user
     * @param telephone the telephone number of the user
     */
    public User(int id, String name, String surname, String address, String email, String username, String password, long telephone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
    }

    /**
     * Generates a random integer value.
     *
     * @return the random integer value
     */
    private int randomInt() {
        double doubleRandom = Math.random() * 4000;
        return (int) doubleRandom;
    }

    /**
     * Returns the ID of the user.
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the user.
     *
     * @return the user name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the surname of the user.
     *
     * @return the user surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Returns the address of the user.
     *
     * @return the user address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the email of the user.
     *
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the telephone number of the user.
     *
     * @return the user telephone number
     */
    public long getTelephone() {
        return telephone;
    }

    /**
     * Returns the username of the user.
     *
     * @return the user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Compares this user with the specified object for equality.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User that = (User) obj;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(username, that.username);
    }

    /**
     * Returns the hash code value for this user.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }
}
