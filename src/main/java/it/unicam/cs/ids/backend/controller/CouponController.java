package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CouponController {

    private List<Coupon> couponList;

    public CouponController() {
        this.couponList = new ArrayList<>();
    }

    /**
     * Returns the list of coupons.
     *
     * @return the list of coupons
     */
    public List<Coupon> getcouponList() {
        return couponList;
    }

    /**
     * Adds a coupon to the database.
     *
     * @param coupon the coupon to be added
     * @throws SQLException if an SQL exception occurs
     */
    public void addCoupon(Coupon coupon) throws SQLException {
        String query = "INSERT INTO coupon (id_opp, id_coupon, coupon_name, pointcost) VALUES('" + coupon.getPointsProgram().getId() + "','" + coupon.getIdCoupon() + "','" + coupon.getCouponName() + "', '" + coupon.getPointCost() + "')";
        DBMSController.insertQuery(query);
    }

    /**
     * Retrieves and returns the list of coupons associated with a branch.
     *
     * @param branch the branch to view coupons for
     * @return the list of coupons associated with the branch
     * @throws SQLException    if an SQL exception occurs
     * @throws DateMistake     if a date mistake occurs
     */
    public List<Coupon> viewCoupon(Branch branch) throws SQLException, DateMistake {
        String table="coupon";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            RegisterController cr=new RegisterController();
            BranchController cp= new BranchController();
            cr.viewCustomers();
            Customer customer =cr.getByID(resultSet.getInt("clientiid_c"));
            cp.viewProgramPointOwner(branch);
            cp.viewLvlProgramOwner(branch);
            FidelityProgram pf=cp.getById(resultSet.getInt("programpointownerid_ppt"));
            if(pf instanceof PointsProgram pp) {
                Coupon cc = new Coupon(resultSet.getInt("id_coupon"), resultSet.getString("coupon_name"),
                        resultSet.getInt("pointcost"), pp, customer);
                this.couponList.add(cc);
            }
        }
        return this.couponList;
    }

    /**
     * Retrieves a coupon by its ID.
     *
     * @param id the ID of the coupon
     * @return the coupon with the specified ID, or null if not found
     */
    public Coupon getByID(int id){
        Coupon coupon = null;
        for (Coupon cc : couponList) {
            if (cc.getIdCoupon() == id)
                coupon = cc;
        }
        if (coupon == null) {
            return null;
        }
        return coupon;
    }

    /**
     * Returns a string representation of the CouponController object.
     *
     * @return a string representation of the CouponController object
     */
    @Override
    public String toString() {
        String string ="";
        for (Coupon coupon : couponList ){
            string+= "id: ["+coupon.getIdCoupon()+"] \n" +
                    "name: ["+coupon.getCouponName()+"] \n" +
                    "points cost: ["+coupon.getPointCost()+"]\n" +
                    "program id: "+coupon+"\n" +
                    "client id: "+coupon+"" +
                    "------------------------------------\n";
        }
        return string;
    }
}
