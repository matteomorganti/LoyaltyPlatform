package it.unicam.cs.ids.backend.model;

/**
 * Represents a coupon that can be redeemed by customers.
 */
public class Coupon {
    private final int idCoupon;
    private final String couponName;
    private final int pointCost;
    private final PointsProgram pointsProgram;
    private final Customer customer;

    /**
     * Constructs a Coupon object with the specified parameters.
     *
     * @param idCoupon The ID of the coupon.
     * @param couponName The name of the coupon.
     * @param pointCost The cost of the coupon in loyalty points.
     * @param pointsProgram The points program associated with the coupon.
     * @param customer The customer who can redeem the coupon.
     */
    public Coupon(int idCoupon, String couponName, int pointCost, PointsProgram pointsProgram, Customer customer) {
        this.idCoupon = idCoupon;
        this.couponName = couponName;
        this.pointCost = pointCost;
        this.pointsProgram = pointsProgram;
        this.customer = customer;
    }

    /**
     * Constructs a Coupon object with the specified parameters.
     * Generates a random ID for the coupon.
     *
     * @param couponName The name of the coupon.
     * @param pointCost The cost of the coupon in loyalty points.
     * @param pointsProgram The points program associated with the coupon.
     * @param customer The customer who can redeem the coupon.
     */
    public Coupon(String couponName, int pointCost, PointsProgram pointsProgram, Customer customer) {
        this.idCoupon = randomInt();
        this.couponName = couponName;
        this.pointCost = pointCost;
        this.pointsProgram = pointsProgram;
        this.customer = customer;
    }

    private int randomInt() {
        double doubleRandom;
        doubleRandom = Math.random() * 4000;
        return (int) doubleRandom;
    }

    /**
     * Returns the ID of the coupon.
     *
     * @return The ID of the coupon.
     */
    public int getIdCoupon() {
        return idCoupon;
    }

    /**
     * Returns the name of the coupon.
     *
     * @return The name of the coupon.
     */
    public String getCouponName() {
        return couponName;
    }

    /**
     * Returns the cost of the coupon in loyalty points.
     *
     * @return The cost of the coupon in loyalty points.
     */
    public int getPointCost() {
        return pointCost;
    }

    /**
     * Returns the points program associated with the coupon.
     *
     * @return The points program associated with the coupon.
     */
    public PointsProgram getPointsProgram() {
        return pointsProgram;
    }

    /**
     * Returns the customer who can redeem the coupon.
     *
     * @return The customer who can redeem the coupon.
     */
    public Customer getCustomer() {
        return customer;
    }
}
