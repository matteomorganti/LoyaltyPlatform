package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.BranchController;

import java.sql.SQLException;
import java.util.Date;

/**
 * Represents a fidelity card associated with a customer and a specific branch.
 */
public class FidelityCard {

    private final int id;
    private String cardName;
    private Date expiration;
    private final Branch branchCard;
    private final Customer customer;
    private int currPoints;
    private int currLevel;
    private int percentLevel;
    private BranchController branchController;

    /**
     * Constructs a fidelity card with the specified card name, expiration date, branch, and customer.
     * The card points and levels are initialized based on the branch's fidelity program.
     *
     * @param cardName   the name of the fidelity card
     * @param expiration the expiration date of the fidelity card
     * @param branchCard the branch associated with the fidelity card
     * @param customer   the customer associated with the fidelity card
     * @throws SQLException if there is an error accessing the database
     */
    public FidelityCard(String cardName, Date expiration, Branch branchCard, Customer customer) throws SQLException {
        this.branchCard = branchCard;
        this.customer = customer;
        this.id = generateRandomId();
        this.cardName = cardName;
        this.expiration = expiration;
        this.branchController = new BranchController();
        initializePointsAndLevels();
    }

    /**
     * Constructs a fidelity card with the specified card name, branch, and customer.
     * The card points and levels are initialized based on the branch's fidelity program.
     *
     * @param cardName   the name of the fidelity card
     * @param branchCard the branch associated with the fidelity card
     * @param customer   the customer associated with the fidelity card
     */
    public FidelityCard(String cardName, Branch branchCard, Customer customer) throws SQLException {
        this.branchCard = branchCard;
        this.customer = customer;
        this.id = generateRandomId();
        this.cardName = cardName;
        this.branchController = new BranchController();
        initializePointsAndLevels();
    }

    /**
     * Constructs a fidelity card with the specified ID, card name, expiration date, branch, customer,
     * current points, current level, and percentage level.
     *
     * @param id           the ID of the fidelity card
     * @param cardName     the name of the fidelity card
     * @param expiration   the expiration date of the fidelity card
     * @param branchCard   the branch associated with the fidelity card
     * @param customer     the customer associated with the fidelity card
     * @param currPoints   the current points on the fidelity card
     * @param currLevel    the current level of the fidelity card
     * @param percentLevel the percentage level of the fidelity card
     */
    public FidelityCard(int id, String cardName, Date expiration, Branch branchCard, Customer customer,
                        int currPoints, int currLevel, int percentLevel) {
        this.id = id;
        this.cardName = cardName;
        this.expiration = expiration;
        this.branchCard = branchCard;
        this.customer = customer;
        this.currPoints = currPoints;
        this.currLevel = currLevel;
        this.percentLevel = percentLevel;
    }

    /**
     * Generates a random ID for the fidelity card.
     *
     * @return the generated random ID
     */
    private int generateRandomId() {
        double doubleRandom = Math.random() * 6000;
        return (int) doubleRandom;
    }

    /**
     * Initializes the points and levels on the fidelity card based on the branch's fidelity program.
     *
     * @throws SQLException if there is an error accessing the database
     */
    private void initializePointsAndLevels() throws SQLException {
        branchController.viewProgramPointOwner(branchCard);
        branchController.viewLvlProgramOwner(branchCard);

        if (branchController.getCountPoints() > 0 && branchController.getCountLevels() == 0) {
            currPoints = 0;
        } else if (branchController.getCountLevels() > 0 && branchController.getCountPoints() == 0) {
            currLevel = 0;
            percentLevel = 0;
        } else if (branchController.getCountPoints() > 0 && branchController.getCountLevels() > 0) {
            currPoints = 0;
            currLevel = 0;
            percentLevel = 0;
        }
    }

    /**
     * Returns the ID of the fidelity card.
     *
     * @return the ID of the fidelity card
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the fidelity card.
     *
     * @return the name of the fidelity card
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Sets the name of the fidelity card.
     *
     * @param cardName the name to set for the fidelity card
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * Returns the expiration date of the fidelity card.
     *
     * @return the expiration date of the fidelity card
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * Sets the expiration date of the fidelity card.
     *
     * @param expiration the expiration date to set for the fidelity card
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * Returns the branch associated with the fidelity card.
     *
     * @return the branch associated with the fidelity card
     */
    public Branch getBranchCard() {
        return branchCard;
    }

    /**
     * Returns the current points on the fidelity card.
     *
     * @return the current points on the fidelity card
     */
    public int getCurrPoints() {
        return currPoints;
    }

    /**
     * Returns the current level of the fidelity card.
     *
     * @return the current level of the fidelity card
     */
    public int getCurrLevel() {
        return currLevel;
    }

    /**
     * Returns the percentage level of the fidelity card.
     *
     * @return the percentage level of the fidelity card
     */
    public int getPercentLevel() {
        return percentLevel;
    }

    /**
     * Returns the customer associated with the fidelity card.
     *
     * @return the customer associated with the fidelity card
     */
    public Customer getCustomer() {
        return customer;
    }
}
