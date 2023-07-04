package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {

    private List<Customer> customers;

    private List<BranchManager> branchOwners;

    private List<Cashier> cashierList;

    private PaymentSystem paymentSystem;

    public RegisterController() {
        this.customers = new ArrayList<>();
        this.branchOwners = new ArrayList<>();
        this.cashierList =new ArrayList<>();
        this.paymentSystem = new PaymentSystem();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<BranchManager> getBranchOwners() {
        return branchOwners;
    }

    public List<Cashier> getCashierList() {
        return cashierList;
    }

    public void ownerRegistration(BranchManager o) throws SQLException {
        if (isDataValid(o)) {
            String query = "INSERT INTO owners (id_o, name_o, surname_o, address_o, email_o, username_o, password, enabled, phonenumber_o) VALUES('" + o.getId() + "','" + o.getName() + "','" + o.getSurname() + "','" + o.getAddress() + "','" + o.getEmail() + "','" + o.getUsername() + "' ,'" + o.getPassword() + "' ,'" + o.isActive() + "', '" + o.getTelephone() + "' )";
            DBMSController.insertQuery(query);
        }
    }

    public void addOwner(BranchManager owner) throws SQLException, DateMistake {
        if (paymentSystem.verifyPayment(owner) == TransactionState.paid) {
            String query = "UPDATE owners SET enabled = 'true' WHERE id_t = '" + owner.getId() + "'";
            DBMSController.insertQuery(query);
        } else {
            throw new DateMistake();
        }
    }

    public BranchManager findById(int id) throws SQLException, DateMistake {
        BranchManager owner = null;
        for (BranchManager t : getAllRetailers()) {
            if (t.getId() == id)
                owner = t;
        }
        if (owner == null) {
            throw new NullPointerException();
        }
        return owner;
    }

    /**
     * metodo per controllare se i dati inseriti sono corretti
     *
     * @param user
     * @return true se i dati sono corretti, false altrimenti.
     */
    private boolean isDataValid(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        return true;
    }

    public List<BranchManager> getAllRetailers() throws SQLException, DateMistake {
        String retailer = "retailers";
        ResultSet resultset = DBMSController.selectAllFromTable(retailer);
        while (resultset.next()) {
            PaymentController conn = new PaymentController();
            conn.viewCreditCard();
            CreditCard daAggiungere = conn.getByID(resultset.getInt("id_cc"));
            BranchManager titolare = new BranchManager(resultset.getInt("id_o"),
                    resultset.getString("name_o"), resultset.getString("surname_t"),
                    resultset.getString("address_o"), resultset.getString("email_o"),
                    resultset.getString("username_o"), resultset.getString("password"),
                    resultset.getInt("phonenumber_o"), resultset.getBoolean("enabled"),
                    daAggiungere);
            this.branchOwners.add(titolare);
        }
        return branchOwners;
    }

    public void customerRegistration(Customer customer) throws SQLException {
        if (isDataValid(customer)) {
            String query = "INSERT INTO customers (id_c, name_c, surname_c, address_c, email_c, username_c, password_c, phonenumber_c) VALUES('" + customer.getId() + "','" + customer.getName() + "','" + customer.getSurname() + "','" + customer.getAddress() + "','" + customer.getEmail() + "','" + customer.getUsername() + "' ,'" + customer.getPassword() + "' ,'" + customer.getTelephone() + "' )";
            DBMSController.insertQuery(query);
        }

    }

    public List<Customer> viewCustomers() throws SQLException {
        String table="customers";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            Customer c= new Customer(resultSet.getInt("id_c"), resultSet.getString("name_c"),resultSet.getString("surname_c"),
                    resultSet.getString("address_c"),resultSet.getString("email_c"),
                    resultSet.getString("username_c"), resultSet.getString("password_c"),
                    resultSet.getInt("phonenumber_c"));
            this.customers.add(c);
        }
        return customers;
    }

    public Customer getByID(int id) throws SQLException {
        viewCustomers();
        for(Customer c: this.customers){
            if(id==c.getId())
                return c;
        }
        return null;
    }

    public void branchCashierRegistration(Cashier cashier) throws SQLException {
        if (isDataValid(cashier)) {
            String query = "INSERT INTO cashier (id_c, name_cs, surname_cs, address_cs, email_cs, username_cs, password, phoneNumber_cs, branch_b) VALUES('" + cashier.getId() + "','" + cashier.getName() + "','" + cashier.getSurname() + "','" + cashier.getAddress() + "','" + cashier.getEmail() + "','" + cashier.getUsername() + "' ,'" + cashier.getPassword() + "' ,'" + cashier.getTelephone() + "', '" + cashier.getBranch().getBranchName() + "' )";
            DBMSController.insertQuery(query);
        }

    }

    public List<Cashier> viewCashiers() throws SQLException, DateMistake {
        String table="cashiers";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            BranchController conn= new BranchController();
            getAllRetailers();
            conn.viewbranch();
            Branch bAdd= conn.findById(resultSet.getString("branchname_b"));
            Cashier bc= new Cashier(resultSet.getInt("id_cs"), resultSet.getString("name_cs"),resultSet.getString("cognome_cp"),
                    resultSet.getString("address_cs"),resultSet.getString("email_cs"),
                    resultSet.getString("username_cs"), resultSet.getString("password"),
                    resultSet.getInt("phonenumber_cs"), bAdd);
            this.cashierList.add(bc);
        }
        return this.cashierList;
    }

    public Cashier getById(int id) throws SQLException, DateMistake {
        viewCashiers();
        for(Cashier c: this.cashierList){
            if(id==c.getId())
                return c;
        }
        return null;
    }

    public void cardUpdate(BranchManager t, CreditCard cc) throws SQLException {
        String query="UPDATE owners SET ccid_cc = '" + cc.getCardNumber() + "' WHERE id_o = '" + t.getId() + "'";
        DBMSController.insertQuery(query);
    }


    public String toStringCustomers() {
        String string ="";
        for (Customer c : customers){
            string+= "id: ["+c.getId()+"] \n" +
                    "name: ["+c.getName()+"] \n" +
                    "surname: ["+c.getSurname()+"]\n" +
                    "address: ["+c.getAddress()+"]\n" +
                    "email: ["+c.getEmail()+"]\n" +
                    "------------------------------------ \n";
        }
        return string;
    }

}