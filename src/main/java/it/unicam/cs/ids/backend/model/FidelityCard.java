package it.unicam.cs.ids.backend.model;

import it.unicam.cs.ids.backend.controller.BranchController;

import java.sql.SQLException;
import java.util.Date;

public class FidelityCard {

    private int id;

    private String cardName;

    private Date expiration;

    private final Branch branchCard;

    private Customer customer;

    private int currPoints;
    private int currlvl;
    private int percentLevel;
    private BranchController branchController;

    public FidelityCard(String cardName, Date expiration, Branch branchCard, Customer customer) throws SQLException {
        this.branchCard = branchCard;
        this.customer = customer;
        this.id=randomInt();
        this.cardName = cardName;
        this.expiration = expiration;
        branchController = new BranchController();
        branchController.viewOwnerPointsProgram(branchCard);
        branchController.viewOwnerPointsProgramLevel(branchCard);
        if(branchController.getCountPoints()>0 && branchController.getCountLevels()==0){
            currPoints =0;
        } else if (branchController.getCountLevels()>0 && branchController.getCountPoints()==0) {
            currlvl =0;
            percentLevel =0;
        }
        else if (branchController.getCountPoints()>0 && branchController.getCountLevels()>0){
            currPoints =0;
            currlvl =0;
            percentLevel =0;
        }
    }

    public FidelityCard(String cardName, Branch branchCard, Customer customer) {
        this.id=randomInt();
        this.cardName = cardName;
        this.branchCard = branchCard;
        this.customer = customer;
        branchController = new BranchController();
        if(branchController.getCountPoints()>0 && branchController.getCountLevels()==0){
            currPoints =0;
        } else if (branchController.getCountLevels()>0 && branchController.getCountPoints()==0) {
            currlvl =0;
            percentLevel =0;
        }
        else if (branchController.getCountPoints()>0 && branchController.getCountLevels()>0){
            currPoints =0;
            currlvl =0;
            percentLevel =0;
        }
    }

    public FidelityCard(int id, String cardName, Date expiration, Branch branchCard, Customer customer, int currPoints, int currlvl, int percentLevel) {
        this.id = id;
        this.cardName = cardName;
        this.expiration = expiration;
        this.branchCard = branchCard;
        this.customer = customer;
        this.currPoints = currPoints;
        this.currlvl = currlvl;
        this.percentLevel = percentLevel;
    }

    private int randomInt(){
        double doubleRandom=Math.random()*6000;
        int intRandom= (int) doubleRandom;
        return intRandom;
    }

    public int  getId() {
        return id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Branch branchCard() {
        return branchCard;
    }

    public int getCurrPoints() {
        return currPoints;
    }

    public int getCurrLevel() {
        return currlvl;
    }

    public int getPercentLevel() {
        return percentLevel;
    }

    public Customer getClient() {
        return customer;
    }
}