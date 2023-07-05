package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.CardController;
import it.unicam.cs.ids.backend.controller.BranchController;
import java.sql.SQLException;

public class Cashier extends User {

    private final BranchController branchController;
    private final CardController cardController;
    private final Branch branch;

    public Cashier(String name, String surname, String address, String businessMail, String username, String password, long pohneNumber, Branch branch) {
        super(name, surname, address,businessMail, username, password, pohneNumber);
        this.branch =branch;
        this.branchController =new BranchController();
        this.cardController = new CardController();
    }

    public Cashier(int id, String name, String surname, String address, String email, String username, String password, long phoneNumber, Branch branch) {
        super(id, name, surname, address, email, username, password, phoneNumber);
        this.branch = branch;
        this.branchController =new BranchController();
        this.cardController = new CardController();
    }
    public Branch getBranch() {
        return branch;
    }

    public void cardLvlUp(int boughtItems, FidelityProgram fidelityProgram, FidelityCard fidelityCard, Coupon coupon) throws SQLException {
        branchController.viewProgramPointOwner(branch);
        branchController.viewProgramPointOwner(branch);
        if(branchController.getCountPoints()>0 && branchController.getCountLevels()==0){
            if(fidelityProgram instanceof PointsProgram pp)
                branchController.addPointsCard(boughtItems, pp, fidelityCard, coupon);
        }
        else if(branchController.getCountPoints()==0 && branchController.getCountLevels()>0){
            if(fidelityProgram instanceof LevellingProgram pl)
                branchController.cardLvlUp(boughtItems, pl, fidelityCard);
        }
        else if(branchController.getCountLevels()>0 && branchController.getCountPoints()>0){
            if (fidelityProgram instanceof PointsProgram pp){
                branchController.addPointsCard(boughtItems, pp, fidelityCard, coupon);
            }
            if(fidelityProgram instanceof LevellingProgram pl){
                branchController.cardLvlUp(boughtItems, pl, fidelityCard);
            }
        }
    }

}
