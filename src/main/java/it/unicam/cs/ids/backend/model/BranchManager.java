package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.FidelityController;
import it.unicam.cs.ids.backend.controller.RegisterController;

import java.sql.SQLException;

/**
 * Represents a branch manager.
 */
public class BranchManager extends User {
    private final RegisterController registerController;
    private final FidelityController fidelityController;
    private boolean active;
    private CreditCard card;

    /**
     * Constructs a BranchManager object with the specified parameters.
     *
     * @param id The ID of the branch manager.
     * @param name The name of the branch manager.
     * @param surname The surname of the branch manager.
     * @param address The address of the branch manager.
     * @param emailBusiness The business email of the branch manager.
     * @param username The username of the branch manager.
     * @param password The password of the branch manager.
     * @param telephone The telephone number of the branch manager.
     * @param active The status of the branch manager (active/inactive).
     * @param card The credit card information of the branch manager.
     */
    public BranchManager(int id, String name, String surname, String address, String emailBusiness, String username, String password, int telephone, boolean active, CreditCard card) {
        super(id, name, surname, address, emailBusiness, username, password, telephone);
        this.active = active;
        this.card = card;
        this.registerController = new RegisterController();
        this.fidelityController = new FidelityController();
    }

    /**
     * Constructs a BranchManager object with the specified parameters.
     *
     * @param id The ID of the branch manager.
     * @param name The name of the branch manager.
     * @param surname The surname of the branch manager.
     * @param address The address of the branch manager.
     * @param email The email of the branch manager.
     * @param username The username of the branch manager.
     * @param password The password of the branch manager.
     * @param telephone The telephone number of the branch manager.
     * @param active The status of the branch manager (active/inactive).
     */
    public BranchManager(int id, String name, String surname, String address, String email, String username, String password, long telephone, boolean active) {
        super(id, name, surname, address, email, username, password, telephone);
        this.active = active;
        this.registerController = new RegisterController();
        this.fidelityController = new FidelityController();
    }

    /**
     * Constructs a BranchManager object with the specified parameters.
     *
     * @param name The name of the branch manager.
     * @param surname The surname of the branch manager.
     * @param address The address of the branch manager.
     * @param email The email of the branch manager.
     * @param username The username of the branch manager.
     * @param password The password of the branch manager.
     * @param telephone The telephone number of the branch manager.
     */
    public BranchManager(String name, String surname, String address, String email, String username, String password, long telephone) {
        super(name, surname, address, email, username, password, telephone);
        this.active = false;
        this.registerController = new RegisterController();
        this.fidelityController = new FidelityController();
    }

    /**
     * Returns the credit card information of the branch manager.
     *
     * @return The credit card information.
     */
    public CreditCard getCard() {
        return card;
    }

    /**
     * Returns the status of the branch manager.
     *
     * @return {@code true} if the branch manager is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the credit card information of the branch manager.
     *
     * @param card The credit card information to set.
     */
    public void setCard(CreditCard card) {
        this.card = card;
    }

    /**
     * Sets the status of the branch manager.
     *
     * @param active The status to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Processes the payment for the branch manager and updates the active status.
     *
     * @throws DateMistake If there is an error with the date.
     * @throws SQLException If there is an error with the database.
     */
    public void processPayment() throws DateMistake, SQLException {
        registerController.addOwner(this);
        active = true;
    }

    /**
     * Adds the branch to a fidelity program with the specified program ID.
     *
     * @param programId The ID of the fidelity program to add the branch to.
     * @throws ExceptionAbilitation If the retailer is not enabled.
     * @throws SQLException If there is an error with the database.
     * @throws DateMistake If there is an error with the date.
     */
    public void addBranchToFidelityProgram(int programId) throws ExceptionAbilitation, SQLException, DateMistake {
        if (active) {
            fidelityController.viewProgramPoint();
            fidelityController.viewProgramLevels();
            fidelityController.addProgramOwner(this, programId);
        } else {
            throw new ExceptionAbilitation("This retailer isn't enabled");
        }
    }
}
