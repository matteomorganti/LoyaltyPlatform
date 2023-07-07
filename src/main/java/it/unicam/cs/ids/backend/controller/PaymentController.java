package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.BranchManager;
import it.unicam.cs.ids.backend.model.CreditCard;
import it.unicam.cs.ids.backend.model.DateMistake;
import it.unicam.cs.ids.backend.model.PlatformManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The PaymentController class handles payment-related operations and credit card management.
 */
public class PaymentController {

    private final List<CreditCard> creditCardList;

    /**
     * Constructs a PaymentController object.
     */
    public PaymentController() {
        this.creditCardList = new ArrayList<>();
    }

    /**
     * Processes a payment for the given branch owner.
     *
     * @param owner the branch owner for whom the payment is being processed
     * @return true if the payment is successful, false otherwise
     * @throws SQLException if there is an error in the SQL query
     */
    public boolean payment(BranchManager owner) throws SQLException {
        if (owner.getCard().getCardBalance() > PlatformManager.getPlatformMembershipCost()) {
            owner.getCard().decreaseBalance(PlatformManager.getPlatformMembershipCost());
            return true;
        }
        return false;
    }

    /**
     * Adds a credit card to the database.
     *
     * @param card the credit card to be added
     * @throws SQLException if there is an error in the SQL query
     */
    public void addCard(CreditCard card) throws SQLException {
        String query = "INSERT INTO creditcard (id_cc, expirationdate, cvv, pin, balance) VALUES('"
                + card.getCardNumber() + "','" + card.getExpirationDate() + "','" + card.getCvv() + "','"
                + card.getPin() + "','" + card.getCardBalance() + "')";
        DBMSController.insertQuery(query);
    }

    /**
     * Retrieves credit card information from the database and populates the creditCardList.
     *
     * @throws SQLException   if there is an error in the SQL query
     * @throws DateMistake    if there is a mistake in the credit card's expiration date
     */
    public void viewCreditCard() throws SQLException, DateMistake {
        String table = "creditcard";
        ResultSet resultSet = DBMSController.selectAllFromTable(table);
        while (resultSet.next()) {
            CreditCard card = new CreditCard(resultSet.getInt("id_cc"), resultSet.getDate("expirationdate"),
                    resultSet.getString("cvv"), resultSet.getString("pin"),
                    resultSet.getDouble("balance"));
            this.creditCardList.add(card);
        }
    }

    /**
     * Retrieves a credit card from the creditCardList based on the given ID.
     *
     * @param id the ID of the credit card to retrieve
     * @return the credit card with the given ID, or null if not found
     */
    public CreditCard getByID(int id) {
        CreditCard creditCard = null;
        for (CreditCard card : creditCardList) {
            if (card.getCardNumber() == id)
                creditCard = card;
        }
        return creditCard;
    }
}