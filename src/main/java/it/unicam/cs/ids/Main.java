package it.unicam.cs.ids;

import it.unicam.cs.ids.backend.controller.*;
import it.unicam.cs.ids.backend.model.*;
import it.unicam.cs.ids.backend.util.ConsoleLog;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;


public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final FidelityController FIDELITY_CONTROLLER = new FidelityController();
    private static final RegisterController REGISTER_CONTROLLER = new RegisterController();
    private static final CardController CARD_CONTROLLER = new CardController();
    private static final BranchController BRANCH_CONTROLLER = new BranchController();
    private static final PaymentController PAYMENT_CONTROLLER = new PaymentController();
    private static final CouponController COUPON_CONTROLLER = new CouponController();

    public static void main(String[] args) throws SQLException, DateMistake, ExceptionAbilitation {
        DBMSController.init();
        boolean flag = false;
        do {
            ConsoleLog.log("Welcome to Loyalty Platform!!");
            ConsoleLog.log("1-Sign in");
            ConsoleLog.log("2-Sign up");
            ConsoleLog.log("3-Exit");
            switch (trialScannerInt()) {
                case 1 -> login();
                case 2 -> registration();
                case 3 -> flag = true;
                default -> ConsoleLog.error("Invalid input, try again!");
            }
        } while (!flag);

        ConsoleLog.log("See you soon!");
    }

    private static void registration() throws SQLException, DateMistake {
        boolean flag = false;
        do {
            ConsoleLog.log("Name:");
            String name = SCANNER.nextLine();
            ConsoleLog.log("Surname:");
            String surname = SCANNER.nextLine();
            ConsoleLog.log("Address");
            String address = SCANNER.nextLine();
            ConsoleLog.log("Business mail:");
            String email = SCANNER.nextLine();
            ConsoleLog.log("Username: ");
            String username = SCANNER.nextLine();
            ConsoleLog.log("Password:");
            String password = SCANNER.nextLine();
            ConsoleLog.log("Phone Number:");
            long telephone = SCANNER.nextLong();

            ConsoleLog.log("Select a role: /n");
            ConsoleLog.log("1-Customer");
            ConsoleLog.log("2-Branch Manager");
            ConsoleLog.log("3-Cashier");
            switch (trialScannerInt()) {
                case 1 -> {
                    Customer customer = new Customer(name, surname, address, email, username, password, telephone);
                    REGISTER_CONTROLLER.customerRegistration(customer);
                    ConsoleLog.log("Excellent, your sign up ended successfully!" +
                            ", your ID is: " + customer.getId() + " ");
                    flag = true;
                }
                case 2 -> {
                    ConsoleLog.log("Enter your branch name:");
                    String branchName = SCANNER.nextLine();
                    ConsoleLog.log("Enter your branch address:");
                    String branchAddress = SCANNER.nextLine();
                    BranchManager owner = new BranchManager(name, surname, address, email, username, password, telephone);
                    REGISTER_CONTROLLER.ownerRegistration(owner);
                    Branch b = new Branch(branchName, branchAddress, owner);
                    BRANCH_CONTROLLER.addBranch(b);
                    ConsoleLog.log("Excellent, your sign up ended successfully!" + ", your ID is: " + owner.getId() + " ");
                    flag = true;
                }

                case 3 -> {
                    ConsoleLog.log("Enter your branch name:");
                    String branchName = SCANNER.nextLine();
                    REGISTER_CONTROLLER.getAllRetailers();
                    BRANCH_CONTROLLER.viewBranch();
                    Branch branch = BRANCH_CONTROLLER.findById(branchName);
                    Cashier cashier = new Cashier(name, surname, address, email, username, password, telephone, branch);
                    REGISTER_CONTROLLER.branchCashierRegistration(cashier);
                    ConsoleLog.log("Excellent, your sign up ended successfully!" +
                            ", your ID is: " + cashier.getId() + " ");
                    flag = true;
                }
                default -> ConsoleLog.error("Invalid option. Try again!");
            }
        } while (!flag);
    }


    private static void login() throws SQLException, DateMistake, ExceptionAbilitation {
        boolean flag = false;
        do {
            ConsoleLog.log("Pick a role: ");
            ConsoleLog.log("1-Customer");
            ConsoleLog.log("2-Owner");
            ConsoleLog.log("3-Cashier");
            ConsoleLog.log("4-manager");
            ConsoleLog.log("5-Exit");
            switch (trialScannerInt()) {
                case 1 -> customerHome();
                case 2 -> ownerHome();
                case 3 -> cashierHome();
                case 4 -> managerHome();
                case 5 -> flag = true;
                default -> ConsoleLog.error("Invalid option. Try again!");
            }
        } while (!flag);
    }

    private static void managerHome() throws SQLException {
        boolean flag = false;
        do {
            ConsoleLog.log("Select an action: ");
            ConsoleLog.log("1- Add fidelity program");
            ConsoleLog.log("2- Delete fidelity program");
            ConsoleLog.log("3- Go back");
            switch (trialScannerInt()) {
                case 1 -> add();
                case 2 -> delete();
                case 3 -> flag = true;
                default -> ConsoleLog.error("Invalid option. Try again!");
            }
        } while (!flag);
    }

    private static void cashierHome() throws SQLException, DateMistake {
        boolean flag = false;
        Cashier currentCashier = null;
        do {
            ConsoleLog.log("Username: ");
            String username = SCANNER.nextLine();
            ConsoleLog.log("Password: ");
            String password = SCANNER.nextLine();
            boolean locale = false;
            for (Cashier cb : REGISTER_CONTROLLER.viewCashiers()) {
                if (cb.getUsername().equals(username) && cb.getPassword().equals(password)) {
                    currentCashier = new Cashier(cb.getId(), cb.getName(), cb.getSurname(), cb.getAddress(), cb.getEmail(), cb.getUsername(), cb.getPassword(), cb.getTelephone(), cb.getBranch());
                    locale = true;
                    break;
                }
            }

            if (!locale) {
                ConsoleLog.log("Wrong username or password!");
                flag = true;
            } else {
                ConsoleLog.log("Welcome " + currentCashier.getUsername() + " : id " + currentCashier.getId());
                ConsoleLog.log("Select an option:");
                ConsoleLog.log("1-Create Card");
                ConsoleLog.log("2-Add points");
                ConsoleLog.log("3-Go back");

                int option = trialScannerInt();

                switch (option) {
                    case 1 -> {
                        createCardForCustomer(currentCashier);
                        flag = true;
                    }
                    case 2 -> {
                        addPointsToCustomer(currentCashier);
                        flag = true;
                    }
                    case 3 -> flag = true;
                    default -> ConsoleLog.error("Invalid option. Please select a valid option.");
                }
            }
        } while (!flag);
    }

    private static void createCardForCustomer(Cashier currentCashier) throws DateMistake, SQLException {
        ConsoleLog.log("Card name:");
        String cardName = SCANNER.nextLine();
        ConsoleLog.log("Expiration date:");
        long cardExpiration = SCANNER.nextLong();
        SCANNER.nextLine(); // Consume the newline character after reading the long
        ConsoleLog.log(REGISTER_CONTROLLER.toStringCustomers());
        ConsoleLog.log("Customer ID:");
        int clientId = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character after reading the int
        Date fcExpiration = new Date(cardExpiration);
        FidelityCard card = new FidelityCard(cardName, fcExpiration, currentCashier.getBranch(), REGISTER_CONTROLLER.getByID(clientId));
        CARD_CONTROLLER.addCard(card);
        ConsoleLog.log("Customer: " + clientId + " card has been created!");
    }

    private static void addPointsToCustomer(Cashier currentCashier) throws SQLException, DateMistake {
        ConsoleLog.log(REGISTER_CONTROLLER.toStringCustomers());
        ConsoleLog.log("Client ID:");
        int clientId = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character after reading the int
        CARD_CONTROLLER.viewFidelityCard(REGISTER_CONTROLLER.getByID(clientId));
        ConsoleLog.log(CARD_CONTROLLER.toString());
        ConsoleLog.log("Card ID");
        int cardId = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character after reading the int
        BRANCH_CONTROLLER.viewProgramPointOwner(currentCashier.getBranch());
        BRANCH_CONTROLLER.viewLvlProgramOwner(currentCashier.getBranch());
        ConsoleLog.log(BRANCH_CONTROLLER.toString());
        ConsoleLog.log("Insert program ID:");
        int programID = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character after reading the int
        ConsoleLog.log("Insert customer bought items list:");
        int expense = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character after reading the int
        COUPON_CONTROLLER.viewCoupon(currentCashier.getBranch());
        ConsoleLog.log(COUPON_CONTROLLER.toString());
        ConsoleLog.log("Insert coupon:");
        int coupon = SCANNER.nextInt();
        SCANNER.nextLine(); // Consume the newline character after reading the int
        currentCashier.cardLvlUp(expense, BRANCH_CONTROLLER.getById(programID), CARD_CONTROLLER.findById(cardId), COUPON_CONTROLLER.getByID(coupon));
        ConsoleLog.log("Points have been added!");
    }


    private static BranchManager authenticateUser() throws SQLException, DateMistake {
        ConsoleLog.log("Username: ");
        String username = SCANNER.nextLine();
        ConsoleLog.log("Password:");
        String password = SCANNER.nextLine();

        for (BranchManager owner : REGISTER_CONTROLLER.getAllRetailers()) {
            if (owner.getUsername().equals(username) && owner.getPassword().equals(password)) {
                return new BranchManager(owner.getId(), owner.getName(), owner.getSurname(), owner.getAddress(), owner.getEmail(), owner.getUsername(), owner.getPassword(), owner.getTelephone(), owner.isActive());
            }
        }

        ConsoleLog.error("Wrong username or password!");
        return null;
    }

    private static void printOwnerWelcomeMessage(BranchManager owner) {
        ConsoleLog.log("Welcome! " + owner.getUsername() + " : id " + owner.getId());
    }

    private static void printOptionsMenu() {
        ConsoleLog.log("Select an option:");
        ConsoleLog.log("1-Pay");
        ConsoleLog.log("2-Add a fidelity program:");
        ConsoleLog.log("3-Remove fidelity program");
        ConsoleLog.log("4-CC options");
        ConsoleLog.log("5-Go back");
    }

    private static void handlePayOption(BranchManager owner) throws DateMistake, SQLException {
        owner = REGISTER_CONTROLLER.findById(owner.getId());
        if (!owner.isActive()) {
            owner.processPayment();
            ConsoleLog.log("You're subscribed!");
        } else {
            ConsoleLog.log("You're already subscribed!");
        }
    }

    private static void handleAddFidelityProgramOption(BranchManager owner) throws SQLException, DateMistake, ExceptionAbilitation {
        owner = REGISTER_CONTROLLER.findById(owner.getId());
        if (owner.isActive()) {
            FIDELITY_CONTROLLER.viewProgramPoint();
            FIDELITY_CONTROLLER.viewProgramLevels();
            ConsoleLog.log(FIDELITY_CONTROLLER.toString());
            ConsoleLog.log("Program Id:");
            int id = SCANNER.nextInt();
            FidelityProgram program1 = FIDELITY_CONTROLLER.findById(id);
            if (program1 instanceof PointsProgram pp) {
                ConsoleLog.log("Set program points: " + id);
                int setPointValue = SCANNER.nextInt();
                ConsoleLog.log("Set the max point value: " + id);
                int setTotalPoints = SCANNER.nextInt();
                pp.setPointValue(setPointValue);
                pp.setTotalPoints(setTotalPoints);
            } else if (program1 instanceof LevellingProgram program) {
                ConsoleLog.log("Edit max lvl " + id);
                int setMaxLevel = SCANNER.nextInt();
                ConsoleLog.log("Edit programs total points " + id);
                int setTotalPoints = SCANNER.nextInt();
                ConsoleLog.log("Edit lvl percentage " + id);
                int lvlPercentage = SCANNER.nextInt();
                program.setMaxLevel(setMaxLevel);
                program.setTotalPoints(setTotalPoints);
                program.setLvlPercentage(lvlPercentage);
            }
            FIDELITY_CONTROLLER.updateProgramManager(program1);
            owner.addBranchToFidelityProgram(program1.getId());
            ConsoleLog.log("The program id: " + id + " has been added!");

            ConsoleLog.log("Coupon creation");
            boolean flagCoupon = false;
            do {
                String couponName = "coupon";
                ConsoleLog.log("Set points needed to unlock this coupon");
                int couponCost = SCANNER.nextInt();
                if (program1 instanceof PointsProgram pp) {
                    Coupon coupon = new Coupon(couponName, couponCost, pp, null);
                    COUPON_CONTROLLER.addCoupon(coupon);
                    ConsoleLog.log("You've added a coupon! " + pp.getName());
                    ConsoleLog.log("Inserisci false per inserire un altro coupon, altrimenti true per uscire");
                    flagCoupon = SCANNER.nextBoolean();
                }
            } while (!flagCoupon);
        } else {
            ConsoleLog.log("You are not eligible to add a fidelity program.");
        }
    }

    private static void handleRemoveFidelityProgramOption(BranchManager owner) throws SQLException, DateMistake {
        if (owner.isActive()) {
            Branch branch = null;
            for (Branch currentBranch : BRANCH_CONTROLLER.viewBranch()) {
                if (currentBranch.getOwner().getId() == owner.getId()) {
                    branch = new Branch(currentBranch.getBranchName(), currentBranch.getAddress(), currentBranch.getOwner());
                }
            }
            BRANCH_CONTROLLER.viewProgramPointOwner(branch);
            BRANCH_CONTROLLER.viewLvlProgramOwner(branch);
            ConsoleLog.log(BRANCH_CONTROLLER.toString());
            ConsoleLog.log("Program ID:");
            int id = SCANNER.nextInt();
            BRANCH_CONTROLLER.deleteById(id);
            ConsoleLog.log("The program " + id + " has been removed!");
        } else {
            ConsoleLog.log("You are not eligible to remove a fidelity program.");
        }
    }

    private static void handleCCOptions(BranchManager owner) throws DateMistake, SQLException {
        boolean flag = false;
        do {
            ConsoleLog.log("1-Add a Card");
            ConsoleLog.log("2-Add balance");
            ConsoleLog.log("3-Go back ");
            int option = trialScannerInt();
            switch (option) {
                case 1 -> {
                    ConsoleLog.log("CVV:");
                    String cvv = SCANNER.nextLine();
                    ConsoleLog.log("PIN:");
                    String pin = SCANNER.nextLine();
                    ConsoleLog.log("Card number:");
                    int cardNumber = SCANNER.nextInt();
                    ConsoleLog.log("Expiration date:");
                    long expirationDate = SCANNER.nextInt();
                    Date expiration = new Date(expirationDate);
                    CreditCard card = new CreditCard(cardNumber, expiration, cvv, pin);
                    PAYMENT_CONTROLLER.addCard(card);
                    REGISTER_CONTROLLER.cardUpdate(owner, card);
                    ConsoleLog.log("Card added");
                }
                case 2 -> {
                    owner = REGISTER_CONTROLLER.findById(owner.getId());
                    if (owner.getCard() != null) {
                        ConsoleLog.log("Insert balance:");
                        int amount = SCANNER.nextInt();
                        owner.getCard().increaseBalance(amount);
                        ConsoleLog.log("Balance added!");
                    } else {
                        ConsoleLog.log("Insert the card");
                    }
                }
                case 3 -> flag = true;
                default -> ConsoleLog.error("Invalid option. Try again!");
            }
        } while (!flag);
    }


    private static void ownerHome() throws SQLException, DateMistake, ExceptionAbilitation {
        boolean flag;
        BranchManager owner;

        do {
            owner = authenticateUser();
            flag = owner == null; // Exit the loop if the owner is not authenticated.

            if (owner != null) {
                printOwnerWelcomeMessage(owner);
                printOptionsMenu();
                int option = trialScannerInt();

                switch (option) {
                    case 1 -> handlePayOption(owner);
                    case 2 -> handleAddFidelityProgramOption(owner);
                    case 3 -> handleRemoveFidelityProgramOption(owner);
                    case 4 -> handleCCOptions(owner);
                    case 5 -> flag = true;
                    default -> ConsoleLog.error("Invalid option. Try again!");
                }
            }
        } while (!flag);
    }


    private static void customerHome() throws SQLException, DateMistake {
        boolean flag=false;
        Customer customer = null;
        do {
            ConsoleLog.log("Username: ");
            String username = SCANNER.nextLine();
            ConsoleLog.log("Password:");
            String password = SCANNER.nextLine();
            boolean locale = false;
            for (Customer currentCustomer : REGISTER_CONTROLLER.viewCustomers()) {
                if (currentCustomer.getUsername().equals(username) && currentCustomer.getPassword().equals(password)) {
                    customer = new Customer(currentCustomer.getId(), currentCustomer.getName(), currentCustomer.getSurname(), currentCustomer.getAddress(), currentCustomer.getEmail(), currentCustomer.getUsername(), currentCustomer.getPassword(), currentCustomer.getTelephone());
                    locale = true;
                }
            }
            if (!locale) {
                ConsoleLog.error("Wrong username or password!");
                flag = true;
            }
            if(locale)
            {
                ConsoleLog.log("Welcome! "+ customer.getUsername()+" : id "+ customer.getId());
                ConsoleLog.log("Select an option:");
                ConsoleLog.log("1-Search a Branch");
                ConsoleLog.log("2-Profile");
                ConsoleLog.log("3-Go Back");
                switch (trialScannerInt()){
                    case 1->{
                        BRANCH_CONTROLLER.viewBranch();
                        ConsoleLog.log(BRANCH_CONTROLLER.branchestoString());
                        ConsoleLog.log("Branch name:");
                        String branchName= SCANNER.nextLine();
                        ConsoleLog.log("New card name:");
                        String cardName= SCANNER.nextLine();
                        ConsoleLog.log("Card expiration date:");
                        long expirationDate= SCANNER.nextLong();
                        Date expiration= new Date(expirationDate);
                        FidelityCard
                                card= new FidelityCard(cardName,expiration, BRANCH_CONTROLLER.findById(branchName), customer);
                        customer.createCard(card);
                        ConsoleLog.log("Excellent your card has been created! "+ branchName);
                        flag=true;
                    }
                    case 2->{
                        CARD_CONTROLLER.viewFidelityCard(customer);
                        ConsoleLog.log("Fidelity card list");
                        ConsoleLog.log(CARD_CONTROLLER.toString());
                        flag=true;
                    }
                    case 3-> flag=true;
                    default -> ConsoleLog.error("Invalid option. Try again!");
                }
            }
        }while(!flag);
    }
    private static void delete() throws SQLException {
        ConsoleLog.log("Name:");
        String name = SCANNER.nextLine();
        ConsoleLog.log("Program ID:");
        int id = SCANNER.nextInt();
        FidelityProgram program = new FidelityProgram(name, id);
        FIDELITY_CONTROLLER.viewProgramPoint();
        FIDELITY_CONTROLLER.viewProgramLevels();
        FIDELITY_CONTROLLER.deleteById(program.getId());
        ConsoleLog.log("The program" + name + " has been deleted!");
    }

    private static void add() throws SQLException {
        ConsoleLog.log("Name:");
        String name = SCANNER.nextLine();
        ConsoleLog.log("Description:");
        String description = SCANNER.nextLine();
        ConsoleLog.log("1- Add points program" +
                "2- Add levelling program ");
        int number = SCANNER.nextInt();
        if (number == 1) {
            FidelityProgram pointsProgram = new PointsProgram(name, description);
            FIDELITY_CONTROLLER.addFidelityProgram(pointsProgram);
        } else if (number == 2) {
            FidelityProgram programLevels = new LevellingProgram(name, description);
            FIDELITY_CONTROLLER.addFidelityProgram(programLevels);
        } else ConsoleLog.error("Program already exist!");

        ConsoleLog.log("Program has been created!");
    }


    private static int trialScannerInt() {
        while (true) {
            try {
                int number = SCANNER.nextInt();
                SCANNER.nextLine();
                return number;
            } catch (Exception e) {
                ConsoleLog.error("Wrong value! ");
            }
        }
    }
}