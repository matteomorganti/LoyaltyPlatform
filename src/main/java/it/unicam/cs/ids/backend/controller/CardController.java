package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.Branch;
import it.unicam.cs.ids.backend.model.Customer;
import it.unicam.cs.ids.backend.model.DateMistake;
import it.unicam.cs.ids.backend.model.FidelityCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CardController class handles the management of fidelity cards.
 */

public class CardController {
    private final List<FidelityCard> fidelityCardList;

    public CardController() {
        this.fidelityCardList = new ArrayList<>();
    }

    /**
     * Adds a fidelity card to the system.
     *
     * @param card the fidelity card to be added
     * @throws DateMistake if the card already exists
     * @throws SQLException if a database error occurs
     */
    public void addCard(FidelityCard card) throws DateMistake, SQLException {
        if(findById(card.getId())==null){
            String query= "INSERT INTO fidelitycard (id_fc, name_fc, expiration_fc, currpoints, currlevel, percentlevel, branch_b, clientid_c ) VALUES('" + card.getId() + "', '" + card.getCardName() + "', '" +card.getExpiration() + "', '" + card.getCurrPoints() + "', '" + card.getCurrLevel() + "', '" + card.getPercentLevel() + "', '" + card.getBranchCard().getBranchName() + "', '" + card.getCustomer().getId() + "')";
            DBMSController.insertQuery(query);
        }
        else throw new DateMistake("The card already exists!");
    }

    /**
     * Finds a fidelity card by its ID.
     *
     * @param id the ID of the fidelity card
     * @return the fidelity card with the specified ID, or null if not found
     */
    public FidelityCard findById(int id) {
        FidelityCard fidelityCard =null;
        for (FidelityCard card: this.fidelityCardList){
            if(card.getId()==id)
                fidelityCard =card;
        }
        if(fidelityCard ==null){
            return null;
        }
        return fidelityCard;
    }

    /**
     * Retrieves and displays all fidelity cards of a customer.
     *
     * @param customer the customer whose cards are to be displayed
     * @throws SQLException if a database error occurs
     * @throws DateMistake if a date mistake occurs
     */
    public void viewFidelityCard(Customer customer) throws SQLException, DateMistake {
        String table="fidelitycard";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            if(customer.getId()== resultSet.getInt("clientid_c")){
                RegisterController cr= new RegisterController();
                cr.viewCustomers();
                BranchController cp= new BranchController();
                cp.viewBranch();
                Branch addBranch = cp.findById(resultSet.getString("branch_b"));
                Customer addCustomer = cr.getByID(resultSet.getInt("clientid_c"));
                FidelityCard cf= new FidelityCard(resultSet.getInt("id_fc"), resultSet.getString("name_fc"),
                        resultSet.getDate("expiration_fc"), addBranch,
                        addCustomer, resultSet.getInt("currpoints"),
                        resultSet.getInt("currlevel"), resultSet.getInt("percentlevel"));
                this.fidelityCardList.add(cf);
            }
        }
    }

    /**
     * Returns a string representation of the CardController object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        String string ="";
        for (FidelityCard card : fidelityCardList){
            string+= "id: ["+ card.getId()+"] \n" +
                    "expiration: ["+ card.getExpiration()+"] \n" +
                    "customer: ["+ card.getCustomer().getUsername()+"] \n" +
                    "branch: ["+card.getBranchCard()+"]\n" +
                    "currentPoints: ["+card.getCurrPoints()+"]\n" +
                    "currentLevel: ["+card.getCurrLevel()+"]\n" +
                    "-------------------------------------------------\n";
        }
        return string;
    }
}
