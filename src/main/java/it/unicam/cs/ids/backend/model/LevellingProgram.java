package it.unicam.cs.ids.backend.model;

/**
 * Represents a levelling program, which is a type of fidelity program.
 * It extends the {@link FidelityProgram} class.
 */
public class LevellingProgram extends FidelityProgram {

    private int maxLevel;
    private int totalPoints;
    private int lvlPercentage;

    /**
     * Constructs a levelling program with the specified ID, name, description, maximum level, total points, and level percentage.
     *
     * @param id            the ID of the levelling program
     * @param name          the name of the levelling program
     * @param description   the description of the levelling program
     * @param maxLevel      the maximum level of the levelling program
     * @param totalPoints   the total points required for the levelling program
     * @param lvlPercentage the level percentage of the levelling program
     */
    public LevellingProgram(int id, String name, String description, int maxLevel, int totalPoints, int lvlPercentage) {
        super(id, name, description);
        this.maxLevel = maxLevel;
        this.totalPoints = totalPoints;
        this.lvlPercentage = lvlPercentage;
    }

    /**
     * Constructs a levelling program with the specified name and description.
     * Initializes the maximum level, total points, and level percentage to 0.
     *
     * @param name        the name of the levelling program
     * @param description the description of the levelling program
     */
    public LevellingProgram(String name, String description) {
        super(name, description);
        this.maxLevel = 0;
        this.totalPoints = 0;
        this.lvlPercentage = 0;
    }

    /**
     * Returns the maximum level of the levelling program.
     *
     * @return the maximum level
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Returns the total points required for the levelling program.
     *
     * @return the total points required
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * Returns the level percentage of the levelling program.
     *
     * @return the level percentage
     */
    public int getLvlPercentage() {
        return lvlPercentage;
    }

    /**
     * Sets the maximum level of the levelling program.
     *
     * @param maxLevel the maximum level to set
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Sets the total points required for the levelling program.
     *
     * @param totalPoints the total points required to set
     */
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * Sets the level percentage of the levelling program.
     *
     * @param lvlPercentage the level percentage to set
     */
    public void setLvlPercentage(int lvlPercentage) {
        this.lvlPercentage = lvlPercentage;
    }
}
