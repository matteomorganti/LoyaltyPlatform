package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.BranchManager;
import it.unicam.cs.ids.backend.model.CreditCard;
import it.unicam.cs.ids.backend.model.DateMistake;
import it.unicam.cs.ids.backend.model.PlatformManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    private final List<CreditCard> creditCardList;

    public PaymentController() {
        this.creditCardList = new ArrayList<>();
    }



    /**
     * se il pagamento è stato effettuato correttamente
     *
     * @return true , altrimenti false;
     */

    public boolean payment(BranchManager t) throws SQLException {
        if (t.getCard().getCardBalance()> PlatformManager.getPlatformMembershipCost()){
            t.getCard().decreaseBalance(PlatformManager.getPlatformMembershipCost());
            return true;
        }
        return false;
    }

    public void addCard(CreditCard cc) throws SQLException {
        String query = "INSERT INTO creditcard (id_cc, expirationdate, cvv, pin, balance) VALUES('" + cc.getCardNumber() + "','" + cc.getExpirationDate() + "','" + cc.getCvv() + "','" + cc.getPin() + "','" + cc.getCardBalance() + "')";
        DBMSController.insertQuery(query);
    }

    public void viewCreditCard() throws SQLException, DateMistake {
        String table="creditcard";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            CreditCard cc= new CreditCard(resultSet.getInt("id_cc"),resultSet.getDate("expirationdate"),
                    resultSet.getString("cvv"),resultSet.getString("pin"),
                    resultSet.getDouble("balance"));
            this.creditCardList.add(cc);
        }
    }

    public CreditCard getByID(int id){
        CreditCard creditCard = null;
        for (CreditCard cc : creditCardList) {
            if (cc.getCardNumber() == id)
                creditCard = cc;
        }
        return creditCard;
    }
}
