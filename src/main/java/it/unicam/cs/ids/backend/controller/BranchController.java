package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The BranchController class manages branches, fidelity programs, and customer data.
 */
public class BranchController {

    private final Set<FidelityProgram> fidelityPrograms;
    private final List<Customer> customerList;
    private final Set<Branch> branches;

    private int pointsCount;
    private int lvlCount;

    /**
     * Creates a new instance of the BranchController class.
     */
    public BranchController() {
        this.fidelityPrograms = new HashSet<>();
        this.customerList = new ArrayList<>();
        this.branches = new HashSet<>();
        pointsCount = 0;
        lvlCount = 0;
    }

    /**
     * Retrieves the fidelity programs associated with the branch.
     *
     * @return The set of fidelity programs.
     */
    public Set<FidelityProgram> getFidelityPrograms() {
        return fidelityPrograms;
    }

    /**
     * Retrieves the count of points.
     *
     * @return The count of points.
     */
    public int getCountPoints() {
        return pointsCount;
    }

    /**
     * Retrieves the count of levels.
     *
     * @return The count of levels.
     */
    public int getCountLevels() {
        return lvlCount;
    }

    /**
     * Retrieves the loyalty program point owner's programs for the given branch.
     *
     * @param branch The branch for which to retrieve the programs.
     * @return The set of fidelity programs.
     * @throws SQLException if a database access error occurs.
     */
    public Set<FidelityProgram> viewProgramPointOwner(Branch branch) throws SQLException {
        String table = "programpointowner";
        ResultSet resultSet = DBMSController.selectAllFromTable(table);
        while (resultSet.next()) {
            int ownerid_o = resultSet.getInt("ownerid_o");
            if (ownerid_o == branch.getOwner().getId()) {
                FidelityProgram pp = new PointsProgram(resultSet.getInt("id_opp"), resultSet.getString("nome_ppt"),
                        resultSet.getString("description_opp"),
                        resultSet.getInt("pointsvalue_opp"), resultSet.getInt("totpoints_opp"));
                this.fidelityPrograms.add(pp);
                pointsCount++;
            }
        }
        return this.fidelityPrograms;
    }

    /**
     * Retrieves the loyalty program level owner's programs for the given branch.
     *
     * @param branch The branch for which to retrieve the programs.
     * @return The set of fidelity programs.
     * @throws SQLException if a database access error occurs.
     */
    public Set<FidelityProgram> viewLvlProgramOwner(Branch branch) throws SQLException {
        String table = "lvlprogramowner";
        ResultSet resultSet = DBMSController.selectAllFromTable(table);
        while (resultSet.next()) {
            int id_titolare = resultSet.getInt("id_o");
            if (id_titolare == branch.getOwner().getId()) {
                FidelityProgram pp = new LevellingProgram(resultSet.getInt("id_olp"), resultSet.getString("nome_plt"),
                        resultSet.getString("description_olp"),
                        resultSet.getInt("maxlvl_olp"), resultSet.getInt("totpoints_olp"),
                        resultSet.getInt("lvlpercetnage_olp"));
                this.fidelityPrograms.add(pp);
                lvlCount++;
            }
        }
        return this.fidelityPrograms;
    }

    /**
     * Adds a new branch to the system.
     *
     * @param branch The branch to add.
     * @throws DateMistake if the branch already exists.
     * @throws SQLException if a database access error occurs.
     * @throws DateMistake   if the branch already exists.
     */
    public void addBranch(Branch branch) throws DateMistake, SQLException {
        for (Branch branch1 : this.branches) {
            if (findById(branch.getBranchName()).equals(branch1.getBranchName())) {
                throw new DateMistake("Branche already exists");
            }
        }
        String query = "INSERT INTO branches (name_b, address_b,owner_o ) VALUES('" + branch.getBranchName() + "','" + branch.getAddress() + "','" + branch.getOwner().getId() + "')";
        DBMSController.insertQuery(query);
    }

    /**
     * Finds a branch by its name.
     *
     * @param name The name of the branch to find.
     * @return The found branch.
     */
    public Branch findById(String name) {
        Branch branch = null;
        for (Branch branch1 : this.branches) {
            if (branch1.getBranchName().equals(name))
                branch = branch1;
        }
        if (branch == null) {
            throw new NullPointerException();
        }
        return branch;
    }

    /**
     * Retrieves the branches from the database.
     *
     * @return The set of branches.
     * @throws SQLException   if a database access error occurs.
     * @throws DateMistake    if there is an issue with the dates.
     */
    public Set<Branch> viewBranch() throws SQLException, DateMistake {
        String table = "branch";
        ResultSet resultSet = DBMSController.selectAllFromTable(table);
        while (resultSet.next()) {
            RegisterController conn = new RegisterController();
            BranchManager owner = conn.findById(resultSet.getInt("ownerid_o"));
            Branch branch = new Branch(resultSet.getString("name_b"),
                    resultSet.getString("address_o"), owner);
            this.branches.add(branch);
        }
        return this.branches;
    }

    /**
     * Adds loyalty points to a fidelity card based on the number of bought items.
     *
     * @param boughtItems The number of items bought.
     * @param pointsProgram          The loyalty program.
     * @param fidelityCard          The fidelity card.
     * @param coupon      The coupon.
     * @return The updated number of earned points.
     * @throws SQLException if a database access error occurs.
     */
    public int addPointsCard(int boughtItems, PointsProgram pointsProgram, FidelityCard fidelityCard, Coupon coupon) throws SQLException {
        int earnedPoints = fidelityCard.getCurrPoints() + (boughtItems / pointsProgram.getPointValue());
        String query = "UPDATE fidelitycard SET currentpoints ='" + earnedPoints + "'WHERE id_cf= '" + fidelityCard.getId() + "'";
        if (earnedPoints >= pointsProgram.getTotalPoints()) {
            //sblocca coupon da ritirare
            String query1 = "UPDATE coupon SET clientiid_c ='" + fidelityCard.getCustomer().getId() + "'WHERE id_coupon= '" + coupon.getIdCoupon() + "'";
            int pointsDetraction = earnedPoints - coupon.getPointCost();
            query = "UPDATE fidelitycard SET currentpoints ='" + pointsDetraction + "'WHERE id_cf= '" + fidelityCard.getId() + "'";
            DBMSController.insertQuery(query1);
        }
        DBMSController.insertQuery(query);
        return earnedPoints;
    }

    /**
     * Increases the loyalty card level based on the number of bought items.
     *
     * @param boughtItems The number of items bought.
     * @param levellingProgram          The leveling program.
     * @param fidelityCard          The fidelity card.
     * @return The updated level percentage.
     * @throws SQLException if a database access error occurs.
     */
    public int cardLvlUp(int boughtItems, LevellingProgram levellingProgram, FidelityCard fidelityCard) throws SQLException {
        int lvlPercentage = fidelityCard.getPercentLevel() + (boughtItems / levellingProgram.getLvlPercentage());
        if (lvlPercentage >= levellingProgram.getTotalPoints() && (fidelityCard.getCurrLevel() < levellingProgram.getMaxLevel())) {
            int subtraction = lvlPercentage - levellingProgram.getTotalPoints();
            int lvlup = fidelityCard.getCurrLevel() + 1;
            String query = "UPDATE fidelitycard SET currentlvl ='" + lvlup + "', lvlpercentage= '" + subtraction + "'WHERE id_fc= '" + fidelityCard.getId() + "'";
            DBMSController.insertQuery(query);

        }
        String query = "UPDATE fidelitycard SET lvlpercecntage= '" + lvlPercentage + "'WHERE id_fc= '" + fidelityCard.getId() + "'";
        DBMSController.insertQuery(query);
        return lvlPercentage;
    }

    /**
     * Deletes a loyalty program by its ID.
     *
     * @param id The ID of the loyalty program to delete.
     * @return true if the deletion is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteById(int id) throws SQLException {
        if (getById(id) == null) {
            throw new NullPointerException("Fidelity program doesn't exist!");
        }
        for (FidelityProgram program : this.fidelityPrograms) {
            if (id == program.getId())
                this.fidelityPrograms.remove(program);
            String query = "";
            if (program instanceof PointsProgram pp) {
                query = "DELETE FROM ownerpointsprogram WHERE nome_opp='" + pp.getName() + "'";
            } else if (program instanceof LevellingProgram pl) {
                query = "DELETE FROM ownerlevellingprogram WHERE name_olp='" + pl.getName() + "';";
            }
            DBMSController.removeQuery(query);
        }
        return false;
    }

    /**
     * Retrieves a loyalty program by its ID.
     *
     * @param id The ID of the loyalty program to retrieve.
     * @return The found loyalty program.
     */
    public FidelityProgram getById(int id) {
        FidelityProgram programFel = null;
        for (FidelityProgram program : this.fidelityPrograms) {
            if (program.getId() == id)
                programFel = program;
        }
        if (programFel == null) {
            throw new NullPointerException();
        }
        return programFel;
    }

    /**
     * Converts the fidelity programs to a string representation.
     *
     * @return The string representation of fidelity programs.
     */
    public String fidelityProgramtoString() {
        StringBuilder string = new StringBuilder();
        for (FidelityProgram program : fidelityPrograms) {
            string.append("id: [").append(program.getId()).append("] \n")
                    .append("name: [").append(program.getName()).append("] \n")
                    .append("description: [").append(program.getDescription()).append("]\n")
                    .append("------------------------------------\n");
        }
        return string.toString();
    }

    /**
     * Converts the branches to a string representation.
     *
     * @return The string representation of branches.
     */
    public String branchestoString() {
        StringBuilder string = new StringBuilder();
        for (Branch branch : branches) {
            string.append("id: [").append(branch.getBranchName()).append("] \n")
                    .append("name: [").append(branch.getAddress()).append("] \n")
                    .append("description").append(branch.getOwner()).append("]\n")
                    .append("------------------------------------\n");
        }
        return string.toString();
    }

}