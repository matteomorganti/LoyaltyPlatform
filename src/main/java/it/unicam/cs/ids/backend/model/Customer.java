package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.CardController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer.
 */
public class Customer extends User {

    private final List<FidelityCard> fidelityCards;
    private final CardController cardController;

    /**
     * Constructs a Customer object with the specified parameters.
     *
     * @param name The name of the customer.
     * @param surname The surname of the customer.
     * @param address The address of the customer.
     * @param email The email of the customer.
     * @param username The username of the customer.
     * @param password The password of the customer.
     * @param telephone The telephone number of the customer.
     */
    public Customer(String name, String surname, String address, String email, String username, String password, long telephone) {
        super(name, surname, address, email, username, password, telephone);
        this.fidelityCards = new ArrayList<>();
        this.cardController = new CardController();
    }

    /**
     * Constructs a Customer object with the specified parameters.
     *
     * @param id The ID of the customer.
     * @param name The name of the customer.
     * @param surname The surname of the customer.
     * @param address The address of the customer.
     * @param email The email of the customer.
     * @param username The username of the customer.
     * @param password The password of the customer.
     * @param telephone The telephone number of the customer.
     */
    public Customer(int id, String name, String surname, String address, String email, String username, String password, long telephone) {
        super(id, name, surname, address, email, username, password, telephone);
        this.fidelityCards = new ArrayList<>();
        this.cardController = new CardController();
    }

    /**
     * Returns the list of fidelity cards associated with the customer.
     *
     * @return The list of fidelity cards.
     */
    public List<FidelityCard> getFidelityCards() {
        return fidelityCards;
    }

    /**
     * Creates a new fidelity card for the customer.
     *
     * @param card The fidelity card to be created.
     * @throws DateMistake if attempting to create multiple cards in the same branch.
     * @throws SQLException if an error occurs while adding the card to the database.
     */
    public void createCard(FidelityCard card) throws DateMistake, SQLException {
        for (FidelityCard fidelityCard : this.fidelityCards) {
            if (fidelityCard.getBranchCard() == fidelityCard.getBranchCard()) {
                throw new DateMistake("You can't create 2 cards in the same branch!");
            }
        }
        this.cardController.addCard(card);
        this.fidelityCards.add(card);
    }
}
