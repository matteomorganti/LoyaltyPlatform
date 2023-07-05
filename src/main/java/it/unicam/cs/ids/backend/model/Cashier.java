package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.CardController;
import it.unicam.cs.ids.backend.controller.BranchController;
import java.sql.SQLException;

/**
 * Represents a cashier working at a specific branch.
 */
public class Cashier extends User {

    private final BranchController branchController;
    private final CardController cardController;
    private final Branch branch;

    /**
     * Constructs a Cashier object with the specified parameters.
     *
     * @param name The name of the cashier.
     * @param surname The surname of the cashier.
     * @param address The address of the cashier.
     * @param businessMail The business email of the cashier.
     * @param username The username of the cashier.
     * @param password The password of the cashier.
     * @param phoneNumber The phone number of the cashier.
     * @param branch The branch where the cashier works.
     */
    public Cashier(String name, String surname, String address, String businessMail, String username, String password, long phoneNumber, Branch branch) {
        super(name, surname, address, businessMail, username, password, phoneNumber);
        this.branch = branch;
        this.branchController = new BranchController();
        this.cardController = new CardController();
    }

    /**
     * Constructs a Cashier object with the specified parameters.
     *
     * @param id The ID of the cashier.
     * @param name The name of the cashier.
     * @param surname The surname of the cashier.
     * @param address The address of the cashier.
     * @param email The email of the cashier.
     * @param username The username of the cashier.
     * @param password The password of the cashier.
     * @param phoneNumber The phone number of the cashier.
     * @param branch The branch where the cashier works.
     */
    public Cashier(int id, String name, String surname, String address, String email, String username, String password, long phoneNumber, Branch branch) {
        super(id, name, surname, address, email, username, password, phoneNumber);
        this.branch = branch;
        this.branchController = new BranchController();
        this.cardController = new CardController();
    }

    /**
     * Returns the branch where the cashier works.
     *
     * @return The branch where the cashier works.
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Processes the card level up for a customer's fidelity card based on the loyalty program.
     *
     * @param boughtItems The number of items bought by the customer.
     * @param fidelityProgram The loyalty program associated with the customer.
     * @param fidelityCard The customer's fidelity card.
     * @param coupon The coupon used by the customer (if any).
     * @throws SQLException If there is an error with the database.
     */
    public void cardLvlUp(int boughtItems, FidelityProgram fidelityProgram, FidelityCard fidelityCard, Coupon coupon) throws SQLException {
        branchController.viewProgramPointOwner(branch);
        branchController.viewLvlProgramOwner(branch);

        // Check if there are points and no levels
        if (branchController.getCountPoints() > 0 && branchController.getCountLevels() == 0) {
            if (fidelityProgram instanceof PointsProgram pp) {
                branchController.addPointsCard(boughtItems, pp, fidelityCard, coupon);
            }
        }
        // Check if there are levels and no points
        else if (branchController.getCountPoints() == 0 && branchController.getCountLevels() > 0) {
            if (fidelityProgram instanceof LevellingProgram pl) {
                branchController.cardLvlUp(boughtItems, pl, fidelityCard);
            }
        }
        // Check if there are both levels and points
        else if (branchController.getCountLevels() > 0 && branchController.getCountPoints() > 0) {
            if (fidelityProgram instanceof PointsProgram pp) {
                branchController.addPointsCard(boughtItems, pp, fidelityCard, coupon);
            }
            if (fidelityProgram instanceof LevellingProgram pl) {
                branchController.cardLvlUp(boughtItems, pl, fidelityCard);
            }
        }
    }
}
