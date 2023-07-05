package it.unicam.cs.ids;

import it.unicam.cs.ids.backend.controller.*;
import it.unicam.cs.ids.backend.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;


public class Main {

    private static final Scanner sc = new Scanner(System.in);
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
            System.out.println("Welcome to Loyalty Platform!!/n");
            System.out.println("1-Sign in");
            System.out.println("2-Sign up");
            System.out.println("3-Exit");
            switch (trialScannerInt()) {
                case 1 -> login();
                case 2 -> registration();
                case 3 -> flag = true;
            }
        } while (!flag);

        System.out.println("See you soon!");
    }

    private static void registration() throws SQLException, DateMistake, ExceptionAbilitation {
        boolean flag = false;
        do {
            System.out.println("Name:");
            String name = sc.nextLine();
            System.out.println("Surname:");
            String surname = sc.nextLine();
            System.out.println("Address");
            String address = sc.nextLine();
            System.out.println("Business mail:");
            String email = sc.nextLine();
            System.out.println("Username: ");
            String username = sc.nextLine();
            System.out.println("Password:");
            String password = sc.nextLine();
            System.out.println("Phone Number:");
            long telephone = sc.nextLong();

            System.out.println("Select a role: /n");
            System.out.println("1-Customer");
            System.out.println("2-Branch Manager");
            System.out.println("3-Cashier");
            switch (trialScannerInt()) {
                case 1 -> {
                    Customer customer = new Customer(name, surname, address, email, username, password, telephone);
                    REGISTER_CONTROLLER.customerRegistration(customer);
                    System.out.println("Excellent, your sign up ended successfully!" +
                            ", your ID is: " + customer.getId() + " ");
                    flag = true;
                }
                case 2 -> {
                    System.out.println("Enter your branch name:");
                    String branchName = sc.nextLine();
                    System.out.println("Enter your branch address:");
                    String branchAddress = sc.nextLine();
                    BranchManager owner = new BranchManager(name, surname, address, email, username, password, telephone);
                    REGISTER_CONTROLLER.ownerRegistration(owner);
                    Branch b = new Branch(branchName, branchAddress, owner);
                    BRANCH_CONTROLLER.addBranch(b);
                    System.out.println("Excellent, your sign up ended successfully!" +
                            ", your ID is: " + owner.getId() + " ");
                    flag = true;
                }

                case 3 -> {
                    System.out.println("Enter your branch name:");
                    String branchName = sc.nextLine();
                    REGISTER_CONTROLLER.getAllRetailers();
                    BRANCH_CONTROLLER.viewBranch();
                    Branch b = BRANCH_CONTROLLER.findById(branchName);
                    Cashier cashier = new Cashier(name, surname, address, email, username, password, telephone, b);
                    REGISTER_CONTROLLER.branchCashierRegistration(cashier);
                    System.out.println("Excellent, your sign up ended successfully!" +
                            ", your ID is: " + cashier.getId() + " ");
                    flag = true;
                }
            }
        } while (!flag);
    }


    private static void login() throws SQLException, DateMistake, ExceptionAbilitation {
        boolean flag = false;
        do {
            System.out.println("Pick a role: ");
            System.out.println("1-Customer");
            System.out.println("2-Owner");
            System.out.println("3-Cashier");
            System.out.println("4-manager");
            System.out.println("5-Exit");
            switch (trialScannerInt()) {
                case 1 -> customerHome();
                case 2 -> ownerHome();
                case 3 -> cashierHome();
                case 4 -> managerHome();
                case 5 -> flag = true;
            }
        } while (!flag);
    }

    private static void managerHome() throws SQLException {
        boolean flag = false;
        do {
            System.out.println("Select an action: ");
            System.out.println("1- Add fidelity program");
            System.out.println("2- Delete fidelity program");
            System.out.println("3- Go back");
            switch (trialScannerInt()) {
                case 1 -> add();
                case 2 -> delete();
                case 3 -> {
                    flag = true;
                }
            }
        } while (!flag);
    }

    private static void cashierHome() throws SQLException, DateMistake {
        boolean flag = false;
        Cashier cb1 = null;
        do {
            System.out.println("Username ");
            String username = sc.nextLine();
            System.out.println("Password");
            String password = sc.nextLine();
            boolean locale = false;
            for (Cashier cb : REGISTER_CONTROLLER.viewCashiers()) {
                if (cb.getUsername().equals(username) && cb.getPassword().equals(password)) {
                    cb = new Cashier(cb.getId(), cb.getName(), cb.getSurname(), cb.getAddress(), cb.getEmail(), cb.getUsername(), cb.getPassword(), cb.getTelephone(), cb.getBranch());
                    locale = true;
                }
            }
            if (!locale) {
                System.out.println("Wrong username or password");
                flag = true;
            }
            if (locale) {
                System.out.println("Welcome " + cb1.getUsername() + " : id " + cb1.getId() + "");
                System.out.println("Select an option");
                System.out.println("1-Create Card");
                System.out.println("2-Add points");
                System.out.println("3-Go back");
                switch (trialScannerInt()) {
                    case 1 -> {
                        REGISTER_CONTROLLER.viewCustomers();
                        System.out.println("Card name:");
                        String cardName = sc.nextLine();
                        System.out.println("Expiration date:");
                        long cardExpiration = sc.nextLong();
                        System.out.println(REGISTER_CONTROLLER.toStringCustomers());
                        System.out.println("Customer ID:");
                        int clientId = sc.nextInt();
                        Date fcExpiration = new Date(cardExpiration);
                        FidelityCard cf = new FidelityCard(cardName, fcExpiration, cb1.getBranch(), REGISTER_CONTROLLER.getByID(clientId));
                        CARD_CONTROLLER.addCard(cf);
                        System.out.println("Customer: " + clientId + " card has been created!");
                        flag = true;
                    }
                    case 2 -> {
                        REGISTER_CONTROLLER.viewCustomers();
                        System.out.println(REGISTER_CONTROLLER.toStringCustomers());
                        System.out.println("Client ID:");
                        int clientId = sc.nextInt();
                        CARD_CONTROLLER.viewFidelityCard(REGISTER_CONTROLLER.getByID(clientId));
                        System.out.println(CARD_CONTROLLER.toString());
                        System.out.println("Card ID");
                        int cardId = sc.nextInt();
                        BRANCH_CONTROLLER.viewProgramPointOwner(cb1.getBranch());
                        BRANCH_CONTROLLER.viewLvlProgramOwner(cb1.getBranch());
                        System.out.println(BRANCH_CONTROLLER.toString());
                        System.out.println("Insert program ID:");
                        int idPf = sc.nextInt();
                        System.out.println("Insert customer bought items list:");
                        int expense = sc.nextInt();
                        COUPON_CONTROLLER.viewCoupon(cb1.getBranch());
                        System.out.println(COUPON_CONTROLLER.toString());
                        System.out.println("Insert coupon:");
                        int coupon = sc.nextInt();
                        cb1.cardLvlUp(expense, BRANCH_CONTROLLER.getById(idPf), CARD_CONTROLLER.findById(cardId), COUPON_CONTROLLER.getByID(coupon));
                        System.out.println("Points have been added!");
                        flag = true;
                    }
                    case 3 -> {
                        flag = true;
                    }
                }
            }
        } while (!flag);
    }
    private static void ownerHome() throws SQLException, DateMistake, ExceptionAbilitation {
        boolean flag=false;
        BranchManager owner= null;
        do {
            System.out.println("Username: ");
            String username = sc.nextLine();
            System.out.println("Password:");
            String password = sc.nextLine();
            boolean locale = false;
            for (BranchManager own : REGISTER_CONTROLLER.getAllRetailers()) {
                if (own.getUsername().equals(username) && own.getPassword().equals(password)) {
                    owner= new BranchManager(own.getId(), own.getName(), own.getSurname(), own.getAddress(), own.getEmail(), own.getUsername(), own.getPassword(), own.getTelephone(), own.isActive());
                    locale = true;
                }
            }
            if (!locale) {
                System.out.println("Wrong username or password!");
                flag = true;
            }
            if(locale)
            {
                System.out.println("Welcome! "+ owner.getUsername()+" : id "+owner.getId()+"");
                System.out.println("Select an option:");
                System.out.println("1-Pay");
                System.out.println("2-Add a fidelity program:");
                System.out.println("3-Remove fidelity program");
                System.out.println("4-CC options");
                System.out.println("5-Go back");
                switch (trialScannerInt()){
                    case 1->{
                        owner= REGISTER_CONTROLLER.findById(owner.getId());
                        if(!owner.isActive()){
                            owner.pay();
                            System.out.println("You're subscribed!");
                            flag=true;}
                        else{System.out.println("You're already subscribed!");
                            flag=true;}

                    }
                    case 2->{
                        owner= REGISTER_CONTROLLER.findById(owner.getId());
                        if(owner.isActive()){
                            FIDELITY_CONTROLLER.viewProgramPoint();
                            FIDELITY_CONTROLLER.viewProgramLevels();
                            System.out.println(FIDELITY_CONTROLLER.toString());
                            System.out.println("Program Id:");
                            int id=sc.nextInt();
                            FidelityProgram pf= FIDELITY_CONTROLLER.findById(id);
                            if(pf instanceof PointsProgram pp){
                                System.out.println("Set program points: "+id+"");
                                int setXValuePoint=sc.nextInt();
                                System.out.println("Set the max point value: "+id+"");
                                int setTotalPoints=sc.nextInt();
                                pp.setPointXValue(setXValuePoint);
                                pp.setTotalPoints(setTotalPoints);
                            }
                            else if(pf instanceof LevellingProgram lp){
                                System.out.println("Edit max lvl "+id+"");
                                int setLivelloMax=sc.nextInt();
                                System.out.println("Edit programs total points "+id+"");
                                int setPuntiTot=sc.nextInt();
                                System.out.println("Edit lvl percentage "+id+"");
                                int lvlPercentage=sc.nextInt();
                                lp.setMaxLevel(setLivelloMax);
                                lp.setTotalPoints(setPuntiTot);
                                lp.setLvlPercentage(lvlPercentage);
                            }
                            FIDELITY_CONTROLLER.updateProgramManager(pf);
                            owner.addBranchFidelityProgram(pf.getId());
                            System.out.println("The program id: "+id+" é has been added!");
                            System.out.println("Coupon creation");
                            boolean flagCoupon=false;
                            do {
                                String nomeCoupon = "coupon";
                                System.out.println("Set points needed to unlock this coupon");
                                int costoCoupon = sc.nextInt();
                                if (pf instanceof PointsProgram pp) {
                                    Coupon coupon = new Coupon(nomeCoupon, costoCoupon, pp, null);
                                    COUPON_CONTROLLER.addCoupon(coupon);
                                    System.out.println("You've added a coupon! " + pp.getName() + "");
                                    System.out.println("Inserisci false per inserire un altro coupon, altrimenti true per uscire");
                                    flagCoupon = sc.nextBoolean();
                                }
                            }while (!flagCoupon);
                            flag=true;
                        }
                        else{System.out.println("Y");
                            flag=true;}
                    }
                    case 3->{
                        if(owner.isActive()){
                            Branch branch = null;
                            for(Branch pv: BRANCH_CONTROLLER.viewBranch()){
                                if(pv.getOwner().getId()==owner.getId()){
                                    branch = new Branch(pv.getBranchName(), pv.getAddress(), pv.getOwner());
                                }
                            }
                            BRANCH_CONTROLLER.viewProgramPointOwner(branch);
                            BRANCH_CONTROLLER.viewLvlProgramOwner(branch);
                            System.out.println(BRANCH_CONTROLLER.toString());
                            System.out.println("Program ID:");
                            int id=sc.nextInt();
                            BRANCH_CONTROLLER.deleteById(id);
                            System.out.println("The program "+id+" has been removed!");
                            flag=true;
                        }
                    }
                    case 4->{
                        do {
                            System.out.println("1-Add a Card");
                            System.out.println("2-Add balance");
                            System.out.println("3-Go back ");
                            switch (trialScannerInt()) {
                                case 1 -> {
                                    System.out.println("CVV:");
                                    String cvv = sc.nextLine();
                                    System.out.println("PIN:");
                                    String pin = sc.nextLine();
                                    System.out.println("Card number:");
                                    int numeroCarta = sc.nextInt();
                                    System.out.println("Expiration date:");
                                    long scadenzaDate = sc.nextInt();
                                    Date scadenza= new Date(scadenzaDate);
                                    CreditCard cc=new CreditCard(numeroCarta, scadenza,cvv,pin);
                                    PAYMENT_CONTROLLER.addCard(cc);
                                    REGISTER_CONTROLLER.cardUpdate(owner,cc);
                                    System.out.println("Card added");
                                    flag=true;
                                }
                                case 2 -> {
                                    owner= REGISTER_CONTROLLER.findById(owner.getId());
                                    if(owner.getCard()!=null){
                                        System.out.println("Insert balance:");
                                        int importo= sc.nextInt();
                                        owner.getCard().increaseBalance(importo);
                                        System.out.println("Balance added!");
                                        flag=true;
                                    }
                                    else{System.out.println("Insert the card");}
                                    flag=true;
                                }
                                case 3->{flag=true;}
                            }
                        }while(!flag);
                    }
                    case 5->{flag=true;}
                }
            }
        }while(!flag);
    }


    private static void customerHome() throws SQLException, DateMistake {
        boolean flag=false;
        Customer customer = null;
        do {
            System.out.println("Username: ");
            String username = sc.nextLine();
            System.out.println("Password:");
            String password = sc.nextLine();
            boolean locale = false;
            for (Customer c : REGISTER_CONTROLLER.viewCustomers()) {
                if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                    customer = new Customer(c.getId(), c.getName(), c.getSurname(), c.getAddress(), c.getEmail(), c.getUsername(), c.getPassword(), c.getTelephone());
                    locale = true;
                }
            }
            if (!locale) {
                System.out.println("Wrong username or password!");
                flag = true;
            }
            if(locale)
            {
                System.out.println("Welcome! "+ customer.getUsername()+" : id "+ customer.getId()+"");
                System.out.println("Select an option:");
                System.out.println("1-Search a Branch");
                System.out.println("2-Profile");
                System.out.println("3-Go Back");
                switch (trialScannerInt()){
                    case 1->{
                        BRANCH_CONTROLLER.viewBranch();
                        System.out.println(BRANCH_CONTROLLER.branchestoString());
                        System.out.println("Branch name:");
                        String branchName=sc.nextLine();
                        System.out.println("New card name:");
                        String nomeCarta=sc.nextLine();
                        System.out.println("Card expiration date:");
                        long expirationDate= sc.nextLong();
                        Date expiration= new Date(expirationDate);
                        FidelityCard fc= new FidelityCard(nomeCarta,expiration, BRANCH_CONTROLLER.findById(branchName), customer);
                        customer.createCard(fc);
                        System.out.println("Excellent your card has been created! "+branchName
                                +"");
                        flag=true;
                    }
                    case 2->{
                        CARD_CONTROLLER.viewFidelityCard(customer);
                        System.out.println("Fidelity card list");
                        System.out.println(CARD_CONTROLLER.toString());
                        flag=true;
                    }
                    case 3->{flag=true;}
                }
            }
        }while(!flag);
    }
    private static void delete() throws SQLException {
        System.out.println("Name:");
        String nome = sc.nextLine();
        System.out.println("Program ID:");
        int id = sc.nextInt();
        FidelityProgram fidelityProgram = new FidelityProgram(nome, id);
        FIDELITY_CONTROLLER.viewProgramPoint();
        FIDELITY_CONTROLLER.viewProgramLevels();
        FIDELITY_CONTROLLER.deleteById(fidelityProgram.getId());
        System.out.println("THe program" + nome + " has been deleted!");
    }

    private static void add() throws SQLException {
        System.out.println("Name:");
        String nome = sc.nextLine();
        System.out.println("Description:");
        String descrizione = sc.nextLine();
        System.out.println("Inserire 1- Add points program" +
                "inserire 2- Add levelling program ");
        int number = sc.nextInt();
        if (number == 1) {
            FidelityProgram programPunti = new PointsProgram(nome, descrizione);
            FIDELITY_CONTROLLER.addFidelityProgram(programPunti);
        } else if (number == 2) {
            FidelityProgram programLivelli = new LevellingProgram(nome, descrizione);
            FIDELITY_CONTROLLER.addFidelityProgram(programLivelli);
        } else System.out.println("Program already exist!");

        System.out.println("Program has been created!");
    }


    private static int trialScannerInt() {
        while (true) {
            try {
                int number = sc.nextInt();
                sc.nextLine();
                return number;
            } catch (Exception e) {
                System.out.println("Wrong value! ");
            }
        }
    }
}