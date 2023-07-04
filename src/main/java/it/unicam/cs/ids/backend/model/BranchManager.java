package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.FidelityController;
import it.unicam.cs.ids.backend.controller.RegisterController;

import java.sql.SQLException;

public class BranchManager extends User {
    private final RegisterController signUp;

    private final FidelityController createFidelityProgram;

    private  boolean active;
    private CreditCard card;

    public BranchManager(int id, String name, String surname, String address, String emailBusiness, String username, String password, int telephone, boolean active, CreditCard card) {
        super(id, name, surname, address, emailBusiness, username, password, telephone);
        this.card = card;
        this.active = active;
        this.signUp = new RegisterController();
        this.createFidelityProgram = new FidelityController();
    }

    public BranchManager(int id, String name, String surname, String address, String email, String username, String password, int telephone, boolean active) {
        super(id, name, surname, address, email, username, password, telephone);
        this.active = active;
        this.signUp = new RegisterController();
        this.createFidelityProgram = new FidelityController();
    }

    public BranchManager(String name, String surname, String address, String email, String username, String password, int telephone) {
        super(name, surname, address, email, username, password, telephone);
        this.active =false;
        this.signUp = new RegisterController();
        this.createFidelityProgram = new FidelityController();
    }
    public CreditCard getCard() {
        return card;
    }

    public boolean isActive() {
        return active;
    }

    public void pay() throws DateMistake, SQLException {
        signUp.addOwner(this);
        active =true;
    }

    public void addBranchFidelityProgram(int id) throws ExceptionAbilitation, SQLException, DateMistake {
        if(active){
            this.createFidelityProgram.viewProgramPoint();
            this.createFidelityProgram.viewProgramLevels();
            this.createFidelityProgram.addProgramOwner(this, id);
        }else{
            throw new ExceptionAbilitation("This retailer isn't enabled");
        }
    }
}
