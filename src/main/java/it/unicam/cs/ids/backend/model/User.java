package it.unicam.cs.ids.backend.model;

import java.util.Objects;

public class User {
    private final int id;
    private final String name;
    private final String surname;
    private final String address;
    private final String email;
    private final long telephone;
    private final String username;
    private final String password;

    public User(String name, String surname, String address, String email, String username, String password, long telephone) {
        this.id=randomInt();
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email=email;
        this.username=username;
        this.password=password;
        this.telephone = telephone;
    }

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

    private int randomInt() {
        double doubleRandom;

        doubleRandom=Math.random()*4000;

        return (int ) doubleRandom;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public long getTelephone() {
        return telephone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User that = (User) obj;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }
}