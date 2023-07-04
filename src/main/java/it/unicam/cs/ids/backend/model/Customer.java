package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.CardController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<FidelityCard> fidelityCards;
    private final CardController cardController;


    public Customer(String name, String surname, String address, String email, String username, String password, int phoneNumber) {
        super(name, surname, address, email, username, password, phoneNumber);
        this.fidelityCards = new ArrayList<>();
        this.cardController = new CardController();
    }

    public Customer(int id, String name, String surname, String address, String email, String username, String password, int phoneNumber) {
        super(id, name, surname, address, email, username, password, phoneNumber);
        this.fidelityCards = new ArrayList<>();
        this.cardController = new CardController();
    }

    public List<FidelityCard> getFidelityCards() {
        return fidelityCards;
    }

    public void createCard(FidelityCard cf) throws DateMistake, SQLException {
        for (FidelityCard c: this.fidelityCards){
            if(c.branchCard()==cf.branchCard()){
                throw new DateMistake("You can't create 2 cards in the same branch!");
            }
        }
        this.cardController.addCard(cf);
        this.fidelityCards.add(cf);
    }


}