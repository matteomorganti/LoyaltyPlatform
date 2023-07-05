package it.unicam.cs.ids.backend.model;

/**
 * Represents a branch of a business.
 */
public class Branch {
    private final String branchName;
    private final String address;
    private final BranchManager owner;

    /**
     * Constructs a Branch object.
     *
     * @param branchName The name of the branch.
     * @param address The address of the branch.
     * @param owner The manager of the branch.
     * @throws IllegalArgumentException if any of the input parameters are null or empty.
     */
    public Branch(String branchName, String address, BranchManager owner) {
        if (branchName == null || branchName.isEmpty()) {
            throw new IllegalArgumentException("Branch name cannot be null or empty.");
        }

        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Branch address cannot be null or empty.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Branch owner cannot be null.");
        }

        this.branchName = branchName;
        this.address = address;
        this.owner = owner;
    }

    /**
     * Returns the name of the branch.
     *
     * @return The branch name.
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Returns the address of the branch.
     *
     * @return The branch address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the manager of the branch.
     *
     * @return The branch manager.
     */
    public BranchManager getOwner() {
        return owner;
    }
}
