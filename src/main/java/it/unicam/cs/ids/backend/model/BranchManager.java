package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.FidelityController;
import it.unicam.cs.ids.backend.controller.RegisterController;

import java.sql.SQLException;

public class BranchManager extends User {
    private final RegisterController doRegistration;

    private final FidelityController createFidelityProgram;

    private boolean activated;
    private CreditCard card;

    public BranchManager(int id, String name, String surname, String address, String emailBusiness, String username, String password, int telephone, boolean activated, CreditCard card) {
        super(id, name, surname, address, emailBusiness, username, password, telephone);
        this.card = card;
        this.activated = activated;
        this.doRegistration = new RegisterController();
        this.createFidelityProgram = new FidelityController();
    }

    public BranchManager(int id, String name, String surname, String address, String email, String username, String password, int telephone, boolean activated) {
        super(id, name, surname, address, email, username, password, telephone);
        this.activated = activated;
        this.doRegistration = new RegisterController();
        this.createFidelityProgram = new FidelityController();
    }

    public BranchManager(String name, String surname, String address, String email, String username, String password, int telephone) {
        super(name, surname, address, email, username, password, telephone);
        this.activated =false;
        this.doRegistration = new RegisterController();
        this.createFidelityProgram = new FidelityController();
    }
    public CreditCard getCard() {
        return card;
    }

    public boolean isActivated() {
        return activated;
    }

    public void pay() throws DateMistake, SQLException {

    }

    public void addBranchFidelityProgram(int id) throws ExceptionAbilitation, SQLException, DateMistake {
        if(activated){
            this.createFidelityProgram.viewProgramPoint();
            this.createFidelityProgram.viewProgramLevels();
            this.createFidelityProgram.addProgramOwner(this, id);
        }else{
            throw new ExceptionAbilitation("Esercente non abilitato alla piattaforma");
        }
    }
}
