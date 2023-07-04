package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BranchController {

    private Set<FidelityProgram> fidelityPrograms;
    private List<Customer> customerList;
    private Set<Branch> branches;

    private int pointsCount;
    private int lvlCount;

    public BranchController() {
        this.fidelityPrograms = new HashSet<>();
        this.customerList = new ArrayList<>();
        this.branches =new HashSet<>();
        pointsCount =0;
        lvlCount=0;
    }

    public Set<FidelityProgram> getFidelityPrograms() {
        return fidelityPrograms;
    }

    public int getCountPoints() {
        return pointsCount;
    }

    public int getCountLevels() {
        return lvlCount;
    }

    /**
     * Metodo che visualizza il programma a
     * punti del titolare della proprio punto vendita
     * @return il set dei programmi a punti
     * @throws SQLException
     */
    public Set<FidelityProgram> viewProgramPointOwner(Branch pv) throws SQLException {
        String table="programpointowner";
        ResultSet resultset= DBMSController.selectAllFromTable(table);
        while (resultset.next()){
            int id_titolare=resultset.getInt("ownerid_o");
            if(id_titolare==pv.getOwner().getId()) {
                FidelityProgram pp = new PointsProgram(resultset.getInt("id_opp"), resultset.getString("nome_ppt"),
                        resultset.getString("description_opp"),
                        resultset.getInt("pointsvalue_opp"), resultset.getInt("totpoints_opp"));
                this.fidelityPrograms.add(pp);
                pointsCount++;
            }
        }
        return this.fidelityPrograms;
    }

    /**
     * Metodo che visualizza il programma a
     * livelli del titolare della proprio punto vendita
     * @return il set dei programmi a punti
     * @throws SQLException
     */
    public Set<FidelityProgram> viewLvlProgramOwner(Branch pv) throws SQLException {
        String table="lvlprogramowner";
        ResultSet resultset= DBMSController.selectAllFromTable(table);
        while (resultset.next()){
            int id_titolare=resultset.getInt("id_o");
            if(id_titolare==pv.getOwner().getId()) {
                FidelityProgram pp = new LevellingProgram(resultset.getInt("id_olp"), resultset.getString("nome_plt"),
                        resultset.getString("description_olp"),
                        resultset.getInt("maxlvl_olp"), resultset.getInt("totpoints_olp"),
                        resultset.getInt("lvlpercetnage_olp"));
                this.fidelityPrograms.add(pp);
                lvlCount++;
            }
        }
        return this.fidelityPrograms;
    }

    public void addBranch(Branch pv) throws DateMistake,SQLException {
        for(Branch p: this.branches){
            if(findById(pv.getBranchName()).equals(p.getBranchName())){
                throw new DateMistake("Branche already exists");
            }
        }
        String query="INSERT INTO branches (name_b, address_b,owner_o ) VALUES('" + pv.getBranchName() + "','" + pv.getAddress() + "','" + pv.getOwner().getId() + "')";
        DBMSController.insertQuery(query);
    }

    public Branch findById(String nome){
        Branch branch = null;
        for (Branch pv : this.branches) {
            if (pv.getBranchName().equals(nome))
                branch =pv;
        }
        if (branch == null) {
            throw new NullPointerException();
        }
        return branch;
    }

    public Set<Branch> viewbranch() throws SQLException, DateMistake {
        String table = "branch";
        ResultSet resultset = DBMSController.selectAllFromTable(table);
        while (resultset.next()) {
            RegisterController conn = new RegisterController();
            BranchManager titolareDaAggiungere = conn.findById(resultset.getInt("ownerid_o"));
            Branch branch = new Branch(resultset.getString("name_b"),
                    resultset.getString("address_o"), titolareDaAggiungere);
            this.branches.add(branch);
        }
        return this.branches;
    }

    public int addPointsCard(int boughtItems, PointsProgram pp, FidelityCard cf, Coupon coupon) throws SQLException {
        int earnedPoints=cf.getCurrPoints()+(boughtItems/pp.getPointXValue());
        String query="UPDATE fidelitycard SET currentpoints ='"+earnedPoints+"'WHERE id_cf= '"+cf.getId()+"'";
        if(earnedPoints>=pp.getTotalPoints()){
            //sblocca coupon da ritirare
            String query1="UPDATE coupon SET clientiid_c ='"+cf.getClient().getId()+"'WHERE id_coupon= '"+coupon.getIdCoupon()+"'";
            int pointsDetraction=earnedPoints-coupon.getPointCost();
            query="UPDATE fidelitycard SET currentpoints ='"+pointsDetraction+"'WHERE id_cf= '"+cf.getId()+"'";
            DBMSController.insertQuery(query1);
        }
        DBMSController.insertQuery(query);
        return earnedPoints;

    }

    public int cardLvlUp(int boughtItems, LevellingProgram pl, FidelityCard cf) throws SQLException {
        int lvlPercentage=cf.getPercentLevel()+(boughtItems/pl.getLvlPercentage());
        if(lvlPercentage>=pl.getTotalPoints()){
            if(cf.getCurrLevel()<pl.getMaxLevel()){
                int subtraction=lvlPercentage-pl.getTotalPoints();
                int lvlup=cf.getCurrLevel()+1;
                String query="UPDATE fidelitycard SET currentlvl ='"+lvlup+"', lvlpercentage= '"+subtraction+"'WHERE id_fc= '"+cf.getId()+"'";
                DBMSController.insertQuery(query);
            }
        }
        String query="UPDATE fidelitycard SET lvlpercecntage= '"+lvlPercentage+"'WHERE id_fc= '"+cf.getId()+"'";
        DBMSController.insertQuery(query);
        return lvlPercentage;
    }

    public boolean deleteById(int id) throws SQLException {
        if (getById(id) == null) {
            throw new NullPointerException("Fidelity program doesn't exist!");
        }
        for (FidelityProgram p : this.fidelityPrograms) {
            if (id == p.getId())
                this.fidelityPrograms.remove(p);
            String query = "";
            if (p instanceof PointsProgram pp) {
                query = "DELETE FROM ownerpointsprogram WHERE nome_opp='" + pp.getName() + "'";
            } else if (p instanceof LevellingProgram pl) {
                query = "DELETE FROM ownerlevellingprogram WHERE name_olp='" + pl.getName() + "';";
            }
            DBMSController.removeQuery(query);
            return true;
        }
        return false;
    }

    public FidelityProgram getById(int id) {
        FidelityProgram programFel = null;
        for (FidelityProgram p : this.fidelityPrograms) {
            if (p.getId() == id)
                programFel = p;
        }
        if (programFel == null) {
            throw new NullPointerException();
        }
        return programFel;
    }
    public String fidelityProgramtoString() {
        String string ="";
        for (FidelityProgram pf : fidelityPrograms){
            string+= "id: ["+pf.getId()+"] \n" +
                    "name: ["+pf.getName()+"] \n" +
                    "description: ["+pf.getDescription()+"]\n" +
                    "------------------------------------\n";
        }
        return string;
    }

    public String BranchestoString() {
        String string ="";
        for (Branch pv : branches){
            string+= "id: ["+ pv.getBranchName()+"] \n" +
                    "name: ["+ pv.getAddress()+"] \n" +
                    "description"+ pv.getOwner()+"]\n" +
                    "------------------------------------\n";
        }
        return string;
    }

}