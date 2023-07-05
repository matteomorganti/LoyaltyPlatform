package it.unicam.cs.ids.backend.model;

/**
 * Represents a points-based fidelity program in the system.
 */
public class PointsProgram extends FidelityProgram {

    private int pointXValue;
    private int totalPoints;

    /**
     * Constructs a points-based fidelity program with the specified details.
     *
     * @param id          the ID of the program
     * @param name        the name of the program
     * @param description the description of the program
     * @param pointXValue the value of one point in the program
     * @param totalPoints the total number of points in the program
     */
    public PointsProgram(int id, String name, String description, int pointXValue, int totalPoints) {
        super(id, name, description);
        this.pointXValue = pointXValue;
        this.totalPoints = totalPoints;
    }

    /**
     * Constructs a points-based fidelity program with the specified name and description.
     *
     * @param name        the name of the program
     * @param description the description of the program
     */
    public PointsProgram(String name, String description) {
        super(name, description);
        this.pointXValue = 0;
        this.totalPoints = 0;
    }

    /**
     * Constructs a points-based fidelity program with the specified name and ID.
     *
     * @param name the name of the program
     * @param id   the ID of the program
     */
    public PointsProgram(String name, int id) {
        super(name, id);
        this.pointXValue = 0;
        this.totalPoints = 0;
    }

    /**
     * Gets the value of one point in the program.
     *
     * @return the value of one point
     */
    public int getPointXValue() {
        return pointXValue;
    }

    /**
     * Gets the total number of points in the program.
     *
     * @return the total number of points
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * Sets the value of one point in the program.
     *
     * @param pointXValue the value of one point
     */
    public void setPointXValue(int pointXValue) {
        this.pointXValue = pointXValue;
    }

    /**
     * Sets the total number of points in the program.
     *
     * @param totalPoints the total number of points
     */
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

}
