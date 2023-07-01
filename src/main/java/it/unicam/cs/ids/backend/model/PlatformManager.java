package it.unicam.cs.ids.backend.model;

public class PlatformManager extends User {

    private static final int PLATFORM_MEMBERSHIP_COST = 500;

    public PlatformManager(String name, String surname, String address, String email, String username, String password, int telephone) {
        super(name, surname, address, email, username, password, telephone);
    }
    public static int getPlatformMembershipCost() {
        return PLATFORM_MEMBERSHIP_COST;
    }
}
