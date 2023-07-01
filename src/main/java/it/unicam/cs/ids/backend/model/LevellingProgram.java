package it.unicam.cs.ids.backend.model;

public class LevellingProgram extends FidelityProgram {


    private int maxLevel;
    private int totalPoints;
    private int percentLevelxValue; //Livello per passare alla fase Vip

    public LevellingProgram(int id, String name, String description, int maxLevel, int totalPoints, int percentLevelxValue) {
        super(id, name, description);
        this.maxLevel = maxLevel;
        this.totalPoints = totalPoints;
        this.percentLevelxValue = percentLevelxValue;
    }

    public LevellingProgram(String name, String description) {
        super(name, description);
        this.maxLevel = 0;
        this.totalPoints = 0;
        this.percentLevelxValue = 0;
    }


    public int getMaxLevel() {
        return maxLevel;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getPercentLevelxValue() {
        return percentLevelxValue;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setPercentLevelxValue(int percentLevelxValue) {
        this.percentLevelxValue = percentLevelxValue;
    }
}
