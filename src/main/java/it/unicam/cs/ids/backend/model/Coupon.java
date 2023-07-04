package it.unicam.cs.ids.backend.model;

public class Coupon {
    private int idCoupon;
    private String couponName;
    private int pointCost;

    private PointsProgram pp;
    private Customer c;

    public Coupon(int idCoupon, String couponName, int pointCost, PointsProgram pp, Customer c) {
        this.idCoupon = idCoupon;
        this.couponName = couponName;
        this.pointCost = pointCost;
        this.pp=pp;
        this.c=c;
    }

    public Coupon(String couponName, int pointCost, PointsProgram pp, Customer c) {
        this.idCoupon=randomInt();
        this.couponName = couponName;
        this.pointCost = pointCost;
        this.pp = pp;
        this.c = c;
    }

    private int randomInt() {
        double doubleRandom=0;

        doubleRandom=Math.random()*4000;

        int intRandom=(int ) doubleRandom;
        return intRandom;
    }

    public int getIdCoupon() {
        return idCoupon;
    }

    public String getCouponName() {
        return couponName;
    }

    public int getPointCost() {
        return pointCost;
    }

    public PointsProgram getPp() {
        return pp;
    }

    public Customer getC() {
        return c;
    }
}

}