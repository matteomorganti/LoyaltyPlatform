package it.unicam.cs.ids.backend.model;

public class LevellingProgram extends FidelityProgram {


    private int maxLevel;
    private int totalPoints;
    private int lvlPercentage; 

    public LevellingProgram(int id, String name, String description, int maxLevel, int totalPoints, int lvlPercentage) {
        super(id, name, description);
        this.maxLevel = maxLevel;
        this.totalPoints = totalPoints;
        this.lvlPercentage = lvlPercentage;
    }

    public LevellingProgram(String name, String description) {
        super(name, description);
        this.maxLevel = 0;
        this.totalPoints = 0;
        this.lvlPercentage = 0;
    }


    public int getMaxLevel() {
        return maxLevel;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getLvlPercentage() {
        return lvlPercentage;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setLvlPercentage(int lvlPercentage) {
        this.lvlPercentage = lvlPercentage;
    }
}
