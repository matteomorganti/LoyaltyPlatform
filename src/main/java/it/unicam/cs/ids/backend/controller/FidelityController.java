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

    public List<FidelityProgram> getProgramList() {
        return programList;
    }

    public void addFidelityProgram(FidelityProgram programFel) throws SQLException {
        programList.add(programFel);
        String query = "";
        if (programFel instanceof PointsProgram pointsProgram) {
            query = "INSERT INTO pointsprogram (id_pp, name_pp, description_pp, pointvalue, totpoints ) VALUES('" + pointsProgram.getId() + "','" + pointsProgram.getName() + "','" + pointsProgram.getDescription() + "', '" + pointsProgram.getPointXValue() + "', '" + pointsProgram.getTotalPoints() + "')";
        }
        if (programFel instanceof LevellingProgram levellingProgram) {
            query = "INSERT INTO levellingprogram (id_lp, name_lp, description_lp, maxlvl, totpoints_pl, lvlpercentage ) VALUES('" + levellingProgram.getId() + "','" + levellingProgram.getName() + "','" + levellingProgram.getDescription() + "', '" + levellingProgram.getMaxLevel() + "', '" + levellingProgram.getTotalPoints() + "', '" + levellingProgram.getLvlPercentage() + "')";
        }
        DBMSController.insertQuery(query);
    }

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

    public List<FidelityProgram> viewProgramLevels() throws SQLException {
        ResultSet resultset = DBMSController.selectAllFromTable("programlivelli");
        while (resultset.next()) {
            FidelityProgram lvlProg = new LevellingProgram(resultset.getInt("id_lp"),
                    resultset.getString("name_lp"), resultset.getString("description_lp"),
                    resultset.getInt("maxlvl"), resultset.getInt("totpoints_lp"),
                    resultset.getInt("lvlpercentage"));
            this.programList.add(lvlProg);
        }
        return this.programList;
    }

    public FidelityProgram findById(int id) {
        FidelityProgram programFel = null;
        for (FidelityProgram p : this.programList) {
            if (p.getId() == id)
                programFel = p;
        }
        if (programFel == null) {
            throw new NullPointerException();
        }
        return programFel;
    }

    public boolean deleteById(int id) throws SQLException {
        if (findById(id) == null) {
            throw new NullPointerException("Fidelity program not found");
        }
        for (FidelityProgram p : this.programList) {
            if (id == p.getId())
                this.programList.remove(p);
            String query = "";
            if (p instanceof PointsProgram pp) {
                query = "DELETE FROM pointsprogram WHERE name_pp='" + pp.getName() + "'";
            } else if (p instanceof LevellingProgram pl) {
                query = "DELETE FROM levellingprogram WHERE name_lp='" + pl.getName() + "';";
            }
            DBMSController.removeQuery(query);
            return true;
        }
        return false;
    }

    public void addProgramOwner(BranchManager t, int id) throws SQLException, DateMistake {
        if (findById(id) != null) {
            if (findById(id) instanceof PointsProgram pointsProgram) {
                String query = "INSERT INTO ownerpointsprogram (id_opp, name_opp, description_opp, pointvalue_opp, totpoints_opp, ownerid_o ) VALUES('" + pointsProgram.getId() + "','" + pointsProgram.getName() + "','" + pointsProgram.getDescription() + "', '" + pointsProgram.getPointXValue() + "', '" + pointsProgram.getTotalPoints() + "', '" + t.getId() + "')";
                DBMSController.insertQuery(query);
            } else if (findById(id) instanceof LevellingProgram levellingProgram) {
                String query = "INSERT INT ownerlevellingprogram (id_olp, name_olp, description_olp, maxlvl_olp, totpoints_olp, valorexpercentualelivello_olp, ownerid_o ) VALUES('" + levellingProgram.getId() + "','" + levellingProgram.getName() + "','" + levellingProgram.getDescription() + "', '" + levellingProgram.getMaxLevel() + "', '" + levellingProgram.getTotalPoints() + "', '" + levellingProgram.getLvlPercentage() + "', '" + t.getId() + "')";
                DBMSController.insertQuery(query);
            }
        }else  throw new DateMistake();
    }

    @Override
    public String toString() {
        String string ="";
        for (FidelityProgram pf : programList){
            string+= "id: ["+pf.getId()+"] \n" +
                    "name: ["+pf.getName()+"] \n" +
                    "description: ["+pf.getDescription()+"]\n" +
                    "------------------------------------\n";
        }
        return string;
    }

    public void updateProgramManager(FidelityProgram pf) throws SQLException, DateMistake {
        if (findById(pf.getId()) != null) {
            if (findById(pf.getId()) instanceof PointsProgram pp) {
                String query = "UPDATE pointsprogram SET pointvalue = '" + pp.getPointXValue() + "',totpoints = '" + pp.getTotalPoints() + "' WHERE id_pp = '" + pp.getId() + "'";
                DBMSController.insertQuery(query);
            } else if (findById(pf.getId()) instanceof LevellingProgram pl) {
                String query = "UPDATE levellingprogram  SET maxlvl = '" + pl.getMaxLevel() + "',totpoints_lp = '" + pl.getTotalPoints() + "', lvlpercentage ='" + pl.getLvlPercentage() + "' WHERE id_lp = '" + pl.getId() + "'";
                DBMSController.insertQuery(query);
            }
        } else throw new DateMistake();

    }

}

