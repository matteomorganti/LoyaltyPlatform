package it.unicam.cs.ids.backend.model;

/**
 * Represents a platform manager in the system.
 */
public class PlatformManager extends User {

    private static final int PLATFORM_MEMBERSHIP_COST = 500;

    /**
     * Constructs a platform manager with the specified details.
     *
     * @param name     the name of the platform manager
     * @param surname  the surname of the platform manager
     * @param address  the address of the platform manager
     * @param email    the email of the platform manager
     * @param username the username of the platform manager
     * @param password the password of the platform manager
     * @param telephone the telephone number of the platform manager
     */
    public PlatformManager(String name, String surname, String address, String email, String username, String password, int telephone) {
        super(name, surname, address, email, username, password, telephone);
    }

    /**
     * Returns the cost of platform membership.
     *
     * @return the platform membership cost
     */
    public static int getPlatformMembershipCost() {
        return PLATFORM_MEMBERSHIP_COST;
    }
}
