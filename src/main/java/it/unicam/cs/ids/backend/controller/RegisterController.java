package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {

    private final List<Customer> customers;

    private final List<BranchManager> branchOwners;

    private final List<Cashier> cashierList;

    private final PaymentSystem paymentSystem;

    public RegisterController() {
        this.customers = new ArrayList<>();
        this.branchOwners = new ArrayList<>();
        this.cashierList =new ArrayList<>();
        this.paymentSystem = new PaymentSystem();
    }

    /**
     * Retrieves the list of registered customers.
     *
     * @return The list of registered customers.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Retrieves the list of registered branch owners.
     *
     * @return The list of registered branch owners.
     */
    public List<BranchManager> getBranchOwners() {
        return branchOwners;
    }

    /**
     * Retrieves the list of registered cashiers.
     *
     * @return The list of registered cashiers.
     */
    public List<Cashier> getCashierList() {
        return cashierList;
    }

    /**
     * Registers a new branch owner.
     *
     * @param owner The branch owner to register.
     * @throws SQLException If an SQL exception occurs.
     */
    public void ownerRegistration(BranchManager owner) throws SQLException {
        if (isDataValid(owner)) {
            String query = "INSERT INTO owners (id_o, name_o, surname_o, address_o, email_o, username_o, password, enabled, phonenumber_o) VALUES('" + owner.getId() + "','" + owner.getName() + "','" + owner.getSurname() + "','" + owner.getAddress() + "','" + owner.getEmail() + "','" + owner.getUsername() + "' ,'" + owner.getPassword() + "' ,'" + owner.isActive() + "', '" + owner.getTelephone() + "' )";
            DBMSController.insertQuery(query);
        }
    }

    /**
     * Adds a registered branch owner to the system.
     * Verifies the owner's payment and updates their enabled status accordingly.
     *
     * @param owner The branch owner to add.
     * @throws SQLException   If an SQL exception occurs.
     * @throws DateMistake    If there is an issue with the payment date.
     */
    public void addOwner(BranchManager owner) throws SQLException, DateMistake {
        if (paymentSystem.verifyPayment(owner) == TransactionState.PAID) {
            String query = "UPDATE owners SET enabled = 'true' WHERE id_t = '" + owner.getId() + "'";
            DBMSController.insertQuery(query);
        } else {
            throw new DateMistake();
        }
    }

    /**
     * Finds a branch owner by their ID.
     *
     * @param id The ID of the branch owner to find.
     * @return The branch owner if found.
     * @throws SQLException   If an SQL exception occurs.
     * @throws DateMistake    If there is an issue with the payment date.
     */
    public BranchManager findById(int id) throws SQLException, DateMistake {
        BranchManager owner = null;
        for (BranchManager manager : getAllRetailers()) {
            if (manager.getId() == id)
                owner = manager;
        }
        if (owner == null) {
            throw new NullPointerException();
        }
        return owner;
    }

    /**
     * Checks if the provided user data is valid.
     *
     * @param user The user to validate.
     * @return True if the data is valid, false otherwise.
     */
    private boolean isDataValid(User user) {
        return user.getName() != null && user.getEmail() != null && user.getUsername() != null && user.getPassword() != null;
    }

    /**
     * Retrieves all registered branch owners from the database.
     *
     * @return The list of registered branch owners.
     * @throws SQLException   If an SQL exception occurs.
     * @throws DateMistake    If there is an issue with the payment date.
     */
    public List<BranchManager> getAllRetailers() throws SQLException, DateMistake {
        String retailer = "retailers";
        ResultSet resultset = DBMSController.selectAllFromTable(retailer);
        while (true) {
            assert resultset != null;
            if (!resultset.next()) break;
            PaymentController conn = new PaymentController();
            conn.viewCreditCard();
            CreditCard card = conn.getByID(resultset.getInt("id_cc"));
            BranchManager owner = new BranchManager(resultset.getInt("id_o"),
                    resultset.getString("name_o"), resultset.getString("surname_t"),
                    resultset.getString("address_o"), resultset.getString("email_o"),
                    resultset.getString("username_o"), resultset.getString("password"),
                    resultset.getInt("phonenumber_o"), resultset.getBoolean("enabled"),
                    card);
            this.branchOwners.add(owner);
        }
        return branchOwners;
    }

    /**
     * Registers a new customer.
     *
     * @param customer The customer to register.
     * @throws SQLException If an SQL exception occurs.
     */
    public void customerRegistration(Customer customer) throws SQLException {
        if (isDataValid(customer)) {
            String query = "INSERT INTO customers (id_c, name_c, surname_c, address_c, email_c, username_c, password_c, phonenumber_c) VALUES('" + customer.getId() + "','" + customer.getName() + "','" + customer.getSurname() + "','" + customer.getAddress() + "','" + customer.getEmail() + "','" + customer.getUsername() + "' ,'" + customer.getPassword() + "' ,'" + customer.getTelephone() + "' )";
            DBMSController.insertQuery(query);
        }

    }

    /**
     * Retrieves the list of registered customers from the database.
     *
     * @return The list of registered customers.
     * @throws SQLException If an SQL exception occurs.
     */
    public List<Customer> viewCustomers() throws SQLException {
        String table="customers";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            Customer customer= new Customer(resultSet.getInt("id_c"), resultSet.getString("name_c"),resultSet.getString("surname_c"),
                    resultSet.getString("address_c"),resultSet.getString("email_c"),
                    resultSet.getString("username_c"), resultSet.getString("password_c"),
                    resultSet.getInt("phonenumber_c"));
            this.customers.add(customer);
        }
        return customers;
    }

    /**
     * Finds a customer by their ID.
     *
     * @param id The ID of the customer to find.
     * @return The customer if found, null otherwise.
     * @throws SQLException If an SQL exception occurs.
     */
    public Customer getByID(int id) throws SQLException {
        viewCustomers();
        for(Customer customer: this.customers){
            if(id==customer.getId())
                return customer;
        }
        return null;
    }

    /**
     * Registers a new cashier for a branch.
     *
     * @param cashier The cashier to register.
     * @throws SQLException If an SQL exception occurs.
     */
    public void branchCashierRegistration(Cashier cashier) throws SQLException {
        if (isDataValid(cashier)) {
            String query = "INSERT INTO cashier (id_cs, name_cs, surname_cs, address_cs, email_cs, username_cs, password, phoneNumber_cs, branch_b) VALUES('" + cashier.getId() + "','" + cashier.getName() + "','" + cashier.getSurname() + "','" + cashier.getAddress() + "','" + cashier.getEmail() + "','" + cashier.getUsername() + "' ,'" + cashier.getPassword() + "' ,'" + cashier.getTelephone() + "', '" + cashier.getBranch().getBranchName() + "' )";
            DBMSController.insertQuery(query);
        }

    }

    /**
     * Retrieves the list of registered cashiers from the database.
     *
     * @return The list of registered cashiers.
     * @throws SQLException If an SQL exception occurs.
     * @throws DateMistake  If there is an issue with the payment date.
     */
    public List<Cashier> viewCashiers() throws SQLException, DateMistake {
        String table="cashiers";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (true){
            assert resultSet != null;
            if (!resultSet.next()) break;
            BranchController conn= new BranchController();
            getAllRetailers();
            conn.viewBranch();
            Branch bAdd= conn.findById(resultSet.getString("branchname_b"));
            Cashier cashier= new Cashier(resultSet.getInt("id_cs"), resultSet.getString("name_cs"),resultSet.getString("cognome_cp"),
                    resultSet.getString("address_cs"),resultSet.getString("email_cs"),
                    resultSet.getString("username_cs"), resultSet.getString("password"),
                    resultSet.getInt("phonenumber_cs"), bAdd);
            this.cashierList.add(cashier);
        }
        return this.cashierList;
    }

    /**
     * Finds a cashier by their ID.
     *
     * @param id The ID of the cashier to find.
     * @return The cashier if found, null otherwise.
     * @throws SQLException If an SQL exception occurs.
     * @throws DateMistake  If there is an issue with the payment date.
     */
    public Cashier getById(int id) throws SQLException, DateMistake {
        viewCashiers();
        for(Cashier cashier: this.cashierList){
            if(id==cashier.getId())
                return cashier;
        }
        return null;
    }

    /**
     * Updates the credit card information for a branch owner.
     *
     * @param owner The branch owner to update.
     * @param card    The credit card to set.
     * @throws SQLException If an SQL exception occurs.
     */
    public void cardUpdate(BranchManager owner, CreditCard card) throws SQLException {
        String query="UPDATE owners SET ccid_cc = '" + card.getCardNumber() + "' WHERE id_o = '" + owner.getId() + "'";
        DBMSController.insertQuery(query);
    }

    /**
     * Retrieves a string representation of the registered customers.
     *
     * @return A string representation of the registered customers.
     */
    public String toStringCustomers() {
        String string ="";
        for (Customer customer : customers){
            string+= "id: ["+customer.getId()+"] \n" +
                    "name: ["+customer.getName()+"] \n" +
                    "surname: ["+customer.getSurname()+"]\n" +
                    "address: ["+customer.getAddress()+"]\n" +
                    "email: ["+customer.getEmail()+"]\n" +
                    "------------------------------------ \n";
        }
        return string;
    }

}