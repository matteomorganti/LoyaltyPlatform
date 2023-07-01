package it.unicam.cs.ids.backend.model;

public class PointsProgram extends FidelityProgram {

    private int pointXValue;
    private int totalPoints;
    public PointsProgram(int id, String name, String description, int pointXValue, int totalPoints) {
        super(id, name, description);
        this.pointXValue = pointXValue;
        this.totalPoints = totalPoints;
    }

    public PointsProgram(String name, String description) {
        super(name, description);
        this.pointXValue =0;
        this.totalPoints =0;
    }

    public PointsProgram(String nome, int id) {
        super(nome, id);
        this.pointXValue =0;
        this.totalPoints =0;
    }


    public int getPointXValue() {
        return pointXValue;
    }

    public int getTotalPoints() {
        return totalPoints;
    }



    public void setPointXValue(int pointXValue) {
        this.pointXValue = pointXValue;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

}
