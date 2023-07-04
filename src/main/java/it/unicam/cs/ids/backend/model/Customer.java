package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.CardController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<FidelityCard> carteFedelta;
    private final CardController cardController;


    public Customer(String nome, String cognome, String indirizzo, String email, String username, String password, int telefono) {
        super(nome, cognome, indirizzo, email, username, password, telefono);
        this.carteFedelta = new ArrayList<>();
        this.cardController = new CardController();
    }

    public Customer(int id, String nome, String cognome, String indirizzo, String email, String username, String password, int telefono) {
        super(id, nome, cognome, indirizzo, email, username, password, telefono);
        this.carteFedelta = new ArrayList<>();
        this.cardController = new CardController();
    }

    public List<FidelityCard> getCarteFedelta() {
        return carteFedelta;
    }

    public void creaCarta(FidelityCard cf) throws DateMistake, SQLException {
        for (FidelityCard c: this.carteFedelta){
            if(c.branchCard()==cf.branchCard()){
                throw new DateMistake("Non puoi creare piu di 2 carte di uno stesso Punto Vendita");
            }
        }
        this.cardController.addCard(cf);
        this.carteFedelta.add(cf);
    }


}