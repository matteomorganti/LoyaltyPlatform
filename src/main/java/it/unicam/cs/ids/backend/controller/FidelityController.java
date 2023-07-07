package it.unicam.cs.ids.backend.controller;
import  it.unicam.cs.ids.backend.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class FidelityController {
    private List<FidelityProgram> programList;

    public FidelityController() {
        programList = new ArrayList<>();
    }

    /**
     * Returns the list of fidelity programs.
     *
     * @return the list of fidelity programs
     */
    public List<FidelityProgram> getProgramList() {
        return programList;
    }

    /**
     * Adds a fidelity program to the list and inserts it into the database.
     *
     * @param programFel the fidelity program to add
     * @throws SQLException if an error occurs while executing the SQL query
     */
    public void addFidelityProgram(FidelityProgram programFel) throws SQLException {
        programList.add(programFel);
        String query = "";
        if (programFel instanceof PointsProgram pointsProgram) {
            query = "INSERT INTO pointsprogram (id_pp, name_pp, description_pp, pointvalue, totpoints ) VALUES('" + pointsProgram.getId() + "','" + pointsProgram.getName() + "','" + pointsProgram.getDescription() + "', '" + pointsProgram.getPointValue() + "', '" + pointsProgram.getTotalPoints() + "')";
        }
        if (programFel instanceof LevellingProgram levellingProgram) {
            query = "INSERT INTO levellingprogram (id_lp, name_lp, description_lp, maxlvl, totpoints_pl, lvlpercentage ) VALUES('" + levellingProgram.getId() + "','" + levellingProgram.getName() + "','" + levellingProgram.getDescription() + "', '" + levellingProgram.getMaxLevel() + "', '" + levellingProgram.getTotalPoints() + "', '" + levellingProgram.getLvlPercentage() + "')";
        }
        DBMSController.insertQuery(query);
    }

    /**
     * Retrieves all points-based fidelity programs from the database and returns them as a list.
     *
     * @return the list of points-based fidelity programs
     * @throws SQLException if an error occurs while executing the SQL query
     */
    public List<FidelityProgram> viewProgramPoint() throws SQLException {
        ResultSet resultset1 = DBMSController.selectAllFromTable("pointsprogram");
        while (resultset1.next()) {
            FidelityProgram fidelityProgram = new PointsProgram(resultset1.getInt("id_pp"),
                    resultset1.getString("name_pp"), resultset1.getString("description_pp"),
                    resultset1.getInt("pointvalue"), resultset1.getInt("totpoints"));
            this.programList.add(fidelityProgram);
        }
        return this.programList;
    }

    /**
     * Retrieves all leveling-based fidelity programs from the database and returns them as a list.
     *
     * @return the list of leveling-based fidelity programs
     * @throws SQLException if an error occurs while executing the SQL query
     */
    public List<FidelityProgram> viewProgramLevels() throws SQLException {
        ResultSet resultset = DBMSController.selectAllFromTable("levellingprogram");
        while (resultset.next()) {
            FidelityProgram lvlProg = new LevellingProgram(resultset.getInt("id_lp"),
                    resultset.getString("name_lp"), resultset.getString("description_lp"),
                    resultset.getInt("maxlvl"), resultset.getInt("totpoints_lp"),
                    resultset.getInt("lvlpercentage"));
            this.programList.add(lvlProg);
        }
        return this.programList;
    }

    /**
     * Finds a fidelity program by its ID.
     *
     * @paramid the ID of the fidelity program to find
     * @return the fidelity program with the specified ID
     */
    public FidelityProgram findById(int id) {
        FidelityProgram fidelityProgram = null;
        for (FidelityProgram program : this.programList) {
            if (program.getId() == id)
                fidelityProgram = program;
        }
        if (fidelityProgram == null) {
            throw new NullPointerException();
        }
        return fidelityProgram;
    }

    /**
     * Deletes a fidelity program with the specified ID from the list and the database.
     *
     * @param id the ID of the fidelity program to delete
     * @return true if the fidelity program was successfully deleted, false otherwise
     * @throws SQLException if an error occurs while executing the SQL query
     */
    public boolean deleteById(int id) throws SQLException {
        if (findById(id) == null) {
            throw new NullPointerException("Fidelity program not found");
        }
        for (FidelityProgram program : this.programList) {
            if (id == program.getId())
                this.programList.remove(program);
            String query = "";
            if (program instanceof PointsProgram pointsProgram) {
                query = "DELETE FROM pointsprogram WHERE name_pp='" + pointsProgram.getName() + "'";
            } else if (program instanceof LevellingProgram pl) {
                query = "DELETE FROM levellingprogram WHERE name_lp='" + pl.getName() + "';";
            }
            DBMSController.removeQuery(query);
            return true;
        }
        return false;
    }

    /**
     * Adds a program owner to a fidelity program.
     *
     * @param owner  the branch owner to add as the program owner
     * @param id the ID of the fidelity program
     * @throws SQLException   if an error occurs while executing the SQL query
     * @throws DateMistake    if there is an issue with the date
     */
    public void addProgramOwner(BranchManager owner, int id) throws SQLException, DateMistake {
        if (findById(id) != null) {
            if (findById(id) instanceof PointsProgram pointsProgram) {
                String query = "INSERT INTO ownerpointsprogram (id_opp, name_opp, description_opp, pointvalue_opp, totpoints_opp, ownerid_o ) VALUES('" + pointsProgram.getId() + "','" + pointsProgram.getName() + "','" + pointsProgram.getDescription() + "', '" + pointsProgram.getPointValue() + "', '" + pointsProgram.getTotalPoints() + "', '" + owner.getId() + "')";
                DBMSController.insertQuery(query);
            } else if (findById(id) instanceof LevellingProgram levellingProgram) {
                String query = "INSERT INT ownerlevellingprogram (id_olp, name_olp, description_olp, maxlvl_olp, totpoints_olp, lvlpercentage_olp, ownerid_o ) VALUES('" + levellingProgram.getId() + "','" + levellingProgram.getName() + "','" + levellingProgram.getDescription() + "', '" + levellingProgram.getMaxLevel() + "', '" + levellingProgram.getTotalPoints() + "', '" + levellingProgram.getLvlPercentage() + "', '" + owner.getId() + "')";
                DBMSController.insertQuery(query);
            }
        }else  throw new DateMistake();
    }

    /**
     * Returns a string representation of the FidelityController object.
     *
     * @return a string representation of the FidelityController object
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (FidelityProgram program : programList){
            string.append("id: [").append(program.getId()).append("] \n").append("name: [").append(program.getName()).append("] \n").append("description: [").append(program.getDescription()).append("]\n").append("------------------------------------\n");
        }
        return string.toString();
    }

    /**
     * Updates the program manager for a fidelity program.
     *
     * @param program the fidelity program to update
     * @throws SQLException if an error occurs while executing the SQL query
     * @throws DateMistake  if there is an issue with the date
     */
    public void updateProgramManager(FidelityProgram program) throws SQLException, DateMistake {
        if (findById(program.getId()) != null) {
            if (findById(program.getId()) instanceof PointsProgram pointsProgram) {
                String query = "UPDATE pointsprogram SET pointvalue = '" + pointsProgram.getPointValue() + "',totpoints = '" + pointsProgram.getTotalPoints() + "' WHERE id_pp = '" + pointsProgram.getId() + "'";
                DBMSController.insertQuery(query);
            } else if (findById(program.getId()) instanceof LevellingProgram levellingProgram) {
                String query = "UPDATE levellingprogram  SET maxlvl = '" + levellingProgram.getMaxLevel() + "',totpoints_lp = '" + levellingProgram.getTotalPoints() + "', lvlpercentage ='" + levellingProgram.getLvlPercentage() + "' WHERE id_lp = '" + levellingProgram.getId() + "'";
                DBMSController.insertQuery(query);
            }
        } else throw new DateMistake();

    }

}

