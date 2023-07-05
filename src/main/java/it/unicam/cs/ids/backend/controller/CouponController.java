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

    public List<Coupon> getcouponList() {
        return couponList;
    }

    public void addCoupon(Coupon c) throws SQLException {
        String query = "INSERT INTO coupon (id_opp, id_coupon, coupon_name, pointcost) VALUES('" + c.getPointsProgram().getId() + "','" + c.getIdCoupon() + "','" + c.getCouponName() + "', '" + c.getPointCost() + "')";
        DBMSController.insertQuery(query);
    }

    public List<Coupon> viewCoupon(Branch pv) throws SQLException, DateMistake {
        String table="coupon";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            RegisterController cr=new RegisterController();
            BranchController cp= new BranchController();
            cr.viewCustomers();
            Customer customer =cr.getByID(resultSet.getInt("clientiid_c"));
            cp.viewProgramPointOwner(pv);
            cp.viewLvlProgramOwner(pv);
            FidelityProgram pf=cp.getById(resultSet.getInt("programpointownerid_ppt"));
            if(pf instanceof PointsProgram pp) {
                Coupon cc = new Coupon(resultSet.getInt("id_coupon"), resultSet.getString("coupon_name"),
                        resultSet.getInt("pointcost"), pp, customer);
                this.couponList.add(cc);
            }
        }
        return this.couponList;
    }

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
