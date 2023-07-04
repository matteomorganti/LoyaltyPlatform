package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.Branch;
import it.unicam.cs.ids.backend.model.Customer;
import it.unicam.cs.ids.backend.model.DateMistake;
import it.unicam.cs.ids.backend.model.FidelityCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardController {
    private final List<FidelityCard> fidelityCardList;

    public CardController() {
        this.fidelityCardList = new ArrayList<>();
    }

    public void addCard(FidelityCard c) throws DateMistake, SQLException {
        if(findById(c.getId())==null){
            String query= "INSERT INTO fidelitycard (id_fc, name_fc, expiration_fc, currpoints, currlevel, percentlevel, branch_b, clientid_c ) VALUES('" + c.getId() + "', '" + c.getCardName() + "', '" +c.getExpiration() + "', '" + c.getCurrPoints() + "', '" + c.getCurrLevel() + "', '" + c.getPercentLevel() + "', '" + c.branchCard().getBranchName() + "', '" + c.getClient().getId() + "')";
            DBMSController.insertQuery(query);
        }
        else throw new DateMistake("La carta é gia esistente");
    }

    public FidelityCard findById(int id) {
        FidelityCard fidelityCard =null;
        for (FidelityCard p: this.fidelityCardList){
            if(p.getId()==id)
                fidelityCard =p;
        }
        if(fidelityCard ==null){
            return null;
        }
        return fidelityCard;
    }

    /**
     * Metodo che restituisce
     * tutte le carte di un singolo cliente;
     *
     * @throws SQLException
     */
    public void viewFidelityCard(Customer c) throws SQLException, DateMistake {
        String table="fidelitycard";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            if(c.getId()== resultSet.getInt("clientid_c")){
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
    @Override
    public String toString() {
        String string ="";
        for (FidelityCard cf : fidelityCardList){
            string+= "id: ["+ cf.getId()+"] \n" +
                    "scadenza: ["+ cf.getExpiration()+"] \n" +
                    "cliente: ["+ cf.getClient().getUsername()+"] \n" +
                    "puntovendita: ["+cf.branchCard()+"]\n" +
                    "punticorrenti: ["+cf.getCurrPoints()+"]\n" +
                    "livellocorrente: ["+cf.getCurrLevel()+"]\n" +
                    "-------------------------------------------------\n";
        }
        return string;
    }
}
