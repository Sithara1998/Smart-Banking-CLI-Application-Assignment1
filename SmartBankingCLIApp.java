import java.util.Arrays;
import java.util.Scanner;

public class SmartBankingCLIApp {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[32;1m";
        final String RESET = "\033[0m";

        final String DASHBOARD = "Welcome to Smart Banking";
        final String CREATING_ACCOUT = "Open New Account";
        final String DEPOSIT = "Deposit Money";
        final String WITHDRAWAL = "Withdraw Money";
        final String TRANSFER = "Transfer Money";
        final String CHECK_BALANCE = "Checking Account Balance";
        final String REMOVE_ACCOUNT = "Drop Exisiting Account";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String[][] accountDetails = new String[0][];
        double[] currentBalance = new double[0];

        String screen = DASHBOARD;

        mainloop: do {
            final String APP_TITLE = String.format("%s%s%s",
                    COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");

            switch (screen) {
                case DASHBOARD:
                    System.out.println("\t[1]. Open New Account");
                    System.out.println("\t[2]. Deposit Money");
                    System.out.println("\t[3]. Withdraw Money");
                    System.out.println("\t[4]. Transfer Money");
                    System.out.println("\t[5]. Check Account Balnce");
                    System.out.println("\t[6]. Drop Exixting Account");
                    System.out.println("\t[7]. Exit");
                    System.out.println();
                    System.out.print("\tEnter an option to continue: ");
                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch (option) {
                        case 1:
                            screen = CREATING_ACCOUT;
                            break;
                        case 2:
                            screen = DEPOSIT;
                            break;
                        case 3:
                            screen = WITHDRAWAL;
                            break;
                        case 4:
                            screen = TRANSFER;
                            break;
                        case 5:
                            screen = CHECK_BALANCE;
                            break;
                        case 6:
                            screen = REMOVE_ACCOUNT;
                            break;
                        case 7:
                            System.out.println(CLEAR);
                            System.exit(0);
                        default:
                            continue;
                    }
                    break;

                // Opening new account
                case CREATING_ACCOUT:

                    boolean valid;
                    String name;
                    int id;
                    String accountNum;
                    double initDepo;

                    do {
                        id = accountDetails.length + 1;
                        accountNum = String.format("SDB-%05d", id);
                        System.out.printf("\tID: %s \n", accountNum);
                        System.out.println();

                        valid = true;
                        System.out.print("\tEnter your full name: ");
                        name = SCANNER.nextLine().strip();

                        if (name.isBlank()) {
                            System.out.printf(ERROR_MSG, "Name Can't be empty!");
                            valid = false;
                            continue;
                        }

                        for (int i = 0; i < name.length(); i++) {
                            if (Character.isSpaceChar(name.charAt(i))) {
                                continue;
                            } else if (!(Character.isLetter(name.charAt(i)))) {
                                System.out.printf(ERROR_MSG, "Invalid name!");
                                valid = false;
                                break;
                            }
                        }
                    } while (!valid);

                    do {
                        valid = true;
                        System.out.println();
                        System.out.print("\tInitial Deposite: ");
                        initDepo = SCANNER.nextDouble();
                        SCANNER.nextLine();

                        if (initDepo < 5000) {
                            System.out.printf(ERROR_MSG, "Invalid deposition!");
                            valid = false;
                        }
                    } while (!valid);

                    String[][] tempAccoutDetail = new String[accountDetails.length + 1][3];
                    double[] newCurrentBalnce = new double[currentBalance.length+1];


                    for (int i = 0; i < accountDetails.length; i++) {
                        tempAccoutDetail[i] = accountDetails[i];
                        newCurrentBalnce[i] = currentBalance[i];

                        // newCurrentBalnce[i] = Double.valueOf(accountDetails[i][2]);
                    }

                    tempAccoutDetail[tempAccoutDetail.length - 1][0] = accountNum;
                    tempAccoutDetail[tempAccoutDetail.length - 1][1] = name;
                    tempAccoutDetail[tempAccoutDetail.length - 1][2] = String.format("%.2f", initDepo);

                    newCurrentBalnce[newCurrentBalnce.length-1] = initDepo;

                    accountDetails = tempAccoutDetail;
                    currentBalance = newCurrentBalnce;

                    for (int i = 0; i < accountDetails.length; i++) {
                        System.out.println(Arrays.toString(accountDetails[i]));

                    }
                    System.out.println(Arrays.toString(currentBalance));

                    System.out.println();
                    System.out.printf(SUCCESS_MSG,
                            String.format("\t\t%sSDB-%05d%s: %s%s%s has been created successfully!\n\n",
                                    COLOR_BLUE_BOLD, id, RESET, COLOR_GREEN_BOLD, name, RESET));

                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;
                    screen = DASHBOARD;

                    break;

                // Deposite money UI
                case DEPOSIT:
                    // Account Number validation
                    int index = 0;
                    double depositAmount = 0;
                    String accSub;
                    String acc;

                    acValidationDepo: do {
                        valid = true;
                        System.out.print("Enter your account number: ");
                        acc = SCANNER.nextLine().strip();

                        if (acc.isBlank()) {
                            System.out.printf(ERROR_MSG, "Account number cannot be empty!");
                            valid = false;
                            continue;
                        }
                        if (!(acc.startsWith("SDB-"))) {
                            System.out.printf(ERROR_MSG, "Invalid account number!Try again.");
                            valid = false;
                            continue;
                        } else {
                            accSub = acc.substring(5);
                            for (int i = 0; i < accSub.length(); i++) {
                                if (!Character.isDigit(accSub.charAt(i))) {
                                    System.out.printf(ERROR_MSG, "Invalid Account number format!");
                                    valid = false;
                                    continue acValidationDepo;
                                }
                            }

                            boolean exists = false;
                            for (int i = 0; i < accountDetails.length; i++) {
                                if (acc.equals(accountDetails[i][0])) {
                                    index = i;
                                    // System.out.println(index);
                                    exists = true;
                                    break acValidationDepo;
                                }

                            }
                            if (!exists) {
                                valid = false;
                                System.out.printf(ERROR_MSG, "Account number does not exist");
                                continue;
                            }

                            if (!valid) {
                                System.out.print("\n\tDo you want to try again? (Y/n)");
                                if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                    screen = DASHBOARD;
                                } else {
                                    continue mainloop;
                                }
                            }
                            System.out.println();
                            break;

                        }
                    } while (!valid);
                    
                    System.out.println();
                    System.out.printf("Current Balace: Rs.%,.2f\n", currentBalance[index]);
                    System.out.println();
                    
                    depoAmountLoop:
                    do{
                        System.out.print("Deposit Amount: ");
                        depositAmount = SCANNER.nextDouble();
                        SCANNER.nextLine();
                        if (depositAmount<500){
                            System.out.println("Invalid amount!Enter a value above 500");
                            valid = true;
                            continue depoAmountLoop;
                        }

                    }while(!valid);
                    double newBalance = currentBalance[index]+depositAmount;
                    System.out.printf("\nNew Balance: Rs.%,.2f\n",newBalance);
                    currentBalance[index] = newBalance;
                    System.out.println();
                    
                    System.out.print("\tDo you want to continue (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                        continue;
                    }
                    screen = DASHBOARD;

                break;

                

                // case TRANSFER:

                // case CHECK_BALANCE:

                // case REMOVE_ACCOUNT

                default:
                    continue;
            }

        } while (true);

    }
}