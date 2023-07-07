package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.PaymentController;

import java.sql.SQLException;

/**
 * Represents a payment system used for verifying payments.
 */
public class PaymentSystem {
    private TransactionState payment;
    private final PaymentController paymentState;

    /**
     * Constructs a payment system.
     * Initializes the payment state and the payment controller.
     */
    public PaymentSystem() {
        paymentState = new PaymentController();
    }

    /**
     * Returns the current payment state.
     *
     * @return the payment state
     */
    public TransactionState getPayment() {
        return payment;
    }

    /**
     * Verifies the payment for the specified branch manager.
     *
     * @param owner the branch manager to verify the payment for
     * @return the resulting payment state after verification
     * @throws SQLException if an SQL exception occurs during the payment verification process
     */
    public TransactionState verifyPayment(BranchManager owner) throws SQLException {
        if (paymentState.payment(owner)) {
            payment = TransactionState.PAID;
        } else {
            payment = TransactionState.WAITING;
        }
        return payment;
    }
}
