package it.unicam.cs.ids.backend.model;

public class FidelityProgram {

    private final String name;

    private String description;

    private final int id;


    public FidelityProgram(String name, String description) {
        this.id= randomInt();
        this.name = name;
        this.description = description;
    }

    public FidelityProgram(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public FidelityProgram(String name) {
        this.id=randomInt();
        this.name = name;
    }

    public FidelityProgram(String name, int id) {
        this.name = name;
        this.id = id;
    }
    private int randomInt(){
        double doubleRandom;
        if(this instanceof PointsProgram){
            doubleRandom= Math.random()*1000;
        }else doubleRandom= Math.random()*2000;
        return (int) doubleRandom;
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