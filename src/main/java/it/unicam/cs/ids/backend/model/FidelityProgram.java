package it.unicam.cs.ids.backend.model;

/**
 * Represents a fidelity program.
 */
public class FidelityProgram {

    private final String name;
    private String description;
    private final int id;

    /**
     * Constructs a fidelity program with the specified name and description.
     *
     * @param name        the name of the fidelity program
     * @param description the description of the fidelity program
     */
    public FidelityProgram(String name, String description) {
        this.id = generateRandomId();
        this.name = name;
        this.description = description;
    }

    /**
     * Constructs a fidelity program with the specified ID, name, and description.
     *
     * @param id          the ID of the fidelity program
     * @param name        the name of the fidelity program
     * @param description the description of the fidelity program
     */
    public FidelityProgram(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Constructs a fidelity program with the specified name.
     *
     * @param name the name of the fidelity program
     */
    public FidelityProgram(String name) {
        this.id = generateRandomId();
        this.name = name;
    }

    /**
     * Constructs a fidelity program with the specified name and ID.
     *
     * @param name the name of the fidelity program
     * @param id   the ID of the fidelity program
     */
    public FidelityProgram(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Generates a random ID for the fidelity program.
     *
     * @return the generated random ID
     */
    private int generateRandomId() {
        double doubleRandom;
        if (this instanceof PointsProgram) {
            doubleRandom = Math.random() * 1000;
        } else {
            doubleRandom = Math.random() * 2000;
        }
        return (int) doubleRandom;
    }

    /**
     * Returns the ID of the fidelity program.
     *
     * @return the ID of the fidelity program
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the fidelity program.
     *
     * @return the name of the fidelity program
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the fidelity program.
     *
     * @return the description of the fidelity program
     */
    public String getDescription() {
        return description;
    }
}
