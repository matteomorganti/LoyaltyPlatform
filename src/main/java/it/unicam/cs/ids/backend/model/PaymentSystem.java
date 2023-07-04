package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.PaymentController;

import java.sql.SQLException;

public class PaymentSystem {
    private TransactionState payment;
    private final PaymentController paymentState;
    public PaymentSystem() {
        paymentState = new PaymentController();
    }
    public TransactionState getPayment() {
        return payment;
    }
    public TransactionState verifyPayment(BranchManager t) throws SQLException {
        if (paymentState.payment(t)){
            payment = TransactionState.PAID;
        }else payment = TransactionState.WAITING;
        return payment;
    }
}