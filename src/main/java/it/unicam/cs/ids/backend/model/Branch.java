package it.unicam.cs.ids.backend.model;

public class Branch {

    private final String branchName;
    private final String address;
    private final BranchManager owner;

    public Branch(String branchName , String branchAddress, BranchManager owner) {
        this.branchName = branchName ;
        this.address = branchAddress;
        this.owner = owner;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getAddress() {
        return address;
    }

    public BranchManager getOwner() {
        return owner;
    }
}