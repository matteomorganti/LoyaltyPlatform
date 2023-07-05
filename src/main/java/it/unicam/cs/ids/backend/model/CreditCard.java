package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.DBMSController;
import java.sql.SQLException;
import java.util.Date;

/**
 * Represents a credit card.
 */
public class CreditCard {

    private int cardNumber;
    private Date expirationDate;
    private String cvv;
    private String pin;
    private double cardBalance;

    /**
     * Constructs a CreditCard object with the specified parameters.
     *
     * @param cardNumber The card number.
     * @param expirationDate The expiration date of the card.
     * @param cvv The CVV number of the card.
     * @param pin The PIN of the card.
     */
    public CreditCard(int cardNumber, Date expirationDate, String cvv, String pin) {
        if (pin.length() == 5 && cvv.length() == 3) {
            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.cvv = cvv;
            this.pin = pin;
            this.cardBalance = 0;
        }
    }

    /**
     * Constructs a CreditCard object with the specified parameters.
     *
     * @param cardNumber The card number.
     * @param expirationDate The expiration date of the card.
     * @param cvv The CVV number of the card.
     * @param pin The PIN of the card.
     * @param cardBalance The balance of the card.
     */
    public CreditCard(int cardNumber, Date expirationDate, String cvv, String pin, double cardBalance) {
        if (pin.length() == 5 && cvv.length() == 3) {
            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.cvv = cvv;
            this.pin = pin;
            this.cardBalance = cardBalance;
        }
    }

    /**
     * Increases the balance of the credit card by the specified amount.
     *
     * @param addMoney The amount to be added to the balance.
     * @throws SQLException if an error occurs while updating the card balance in the database.
     */
    public void increaseBalance(double addMoney) throws SQLException {
        this.cardBalance += addMoney;
        String query = "UPDATE creditcard SET cardbalance = '" + this.cardBalance + "' WHERE id_cc = '" + this.cardNumber + "'";
        DBMSController.insertQuery(query);
    }

    /**
     * Decreases the balance of the credit card by the specified amount.
     *
     * @param removeMoney The amount to be removed from the balance.
     * @throws SQLException if an error occurs while updating the card balance in the database.
     */
    public void decreaseBalance(double removeMoney) throws SQLException {
        this.cardBalance -= removeMoney;
        String query = "UPDATE creditcard SET cardbalance = '" + this.cardBalance + "' WHERE id_cc = '" + this.cardNumber + "'";
        DBMSController.insertQuery(query);
    }

    /**
     * Returns the balance of the credit card.
     *
     * @return The balance of the credit card.
     */
    public double getCardBalance() {
        return cardBalance;
    }

    /**
     * Returns the card number.
     *
     * @return The card number.
     */
    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the expiration date of the card.
     *
     * @return The expiration date of the card.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Returns the CVV number of the card.
     *
     * @return The CVV number of the card.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Returns the PIN of the card.
     *
     * @return The PIN of the card.
     */
    public String getPin() {
        return pin;
    }
}
