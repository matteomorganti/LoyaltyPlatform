package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.DBMSController;

import java.sql.SQLException;
import java.util.Date;

public class CreditCard {

    private int cardNumber;

    private Date expirationDate;

    private String cvv;

    private String pin;


    private double cardBalance;

    public CreditCard(int cardNumber, Date expirationDate, String cvv, String pin) {
        if (pin.length()==5 && cvv.length()==3) {
            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.cvv = cvv;
            this.pin = pin;
            this.cardBalance =0;
        }
    }

    public CreditCard(int cardNumber, Date expirationDate, String cvv, String pin, double cardBalance) {
        if (pin.length()==5 && cvv.length()==3) {
            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.cvv = cvv;
            this.pin = pin;
            this.cardBalance = cardBalance;
        }
    }

    public void increaseBalance(int addMoney) throws SQLException {
        this.cardBalance +=addMoney;
        double incremento=this.cardBalance;
        String query="UPDATE creditcard SET cardbalance = '" +incremento+ "' WHERE id_cc = '" + this.getCardNumber() + "'";
        DBMSController.insertQuery(query);
    }

    public void decreaseBalance(int removeMoney) throws SQLException {
        this.cardBalance -=removeMoney;
        double decremento=this.cardBalance;
        String query="UPDATE creditcard SET cardbalance = '" +decremento+ "' WHERE id_cc = '" + this.getCardNumber() + "'";
        DBMSController.insertQuery(query);
    }


    public double getCardBalance() {
        return cardBalance;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public String getPin() {
        return pin;
    }
}
