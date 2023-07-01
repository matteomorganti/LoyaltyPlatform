package it.unicam.cs.ids.backend.model;

public class FidelityProgram {

    private final String name;

    private String description;

    private final int id;


    public FidelityProgram(String name, String description) {
        this.id= 0;
        this.name = name;
        this.description = description;
    }

    public FidelityProgram(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public FidelityProgram(String name) {
        this.id=0;
        this.name = name;
    }

    public FidelityProgram(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public  int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}