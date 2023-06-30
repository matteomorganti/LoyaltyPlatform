package it.unicam.cs.ids.backend.model;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String surname;
    private String address;
    private String email;
    private int telephone;
    private String username;
    private String password;

    public User(String name, String surname, String address, String email, String username, String password, int telephone) {
        this.id=randomInt();
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email=email;
        this.username=username;
        this.password=password;
        this.telephone = telephone;
    }

    public User(int id, String name, String surname, String address, String email, String username, String password, int telephone) {
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
        double doubleRandom=0;

        doubleRandom=Math.random()*4000;

        int intRandom=(int ) doubleRandom;
        return intRandom;
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

    public int getTelephone() {
        return telephone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }
}