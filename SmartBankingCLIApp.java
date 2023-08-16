import java.util.Arrays;
import java.util.Scanner;

public class SmartBankingCLIApp{
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args){

        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[32;1m";
        final String COLOR_PURPLE_BOLD = "\033[35;m";
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

        String[] accountCreater = new String[0];
        String[] accountNumber = new String[0];
        double[] intialDeposit = new double[][0];
        double[] currentBalance = new double[][0];

        String screen = DASHBOARD;
        mainloop:
        do{
            final String APP_TITLE = String.format("%s%s%s",
            COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");

            switch(screen){
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

                    switch (option){
                        case 1: screen = CREATING_ACCOUT; break;
                        case 2: screen = DEPOSIT; break;
                        case 3: screen = WITHDRAWAL; break;
                        case 4: screen = TRANSFER;break;
                        case 5: screen = CHECK_BALANCE; break;
                        case 6: screen = REMOVE_ACCOUNT; break;
                        case 7: System.out.println(CLEAR); System.exit(0);
                        default: continue;
                    }
                break;
                
                //opening new account
                case CREATING_ACCOUT:
                    
                    boolean valid;
                    String name;
                    int id;
                    String accountNum;
                    double initDepo;
                    
                    do{
                        id = accountCreater.length + 1; 
                        accountNum = String.format("SDB-%05d", id);
                        System.out.printf("\tID: %s \n", accountNum);
                        System.out.println();
                        
                        valid = true;
                        System.out.print("\tEnter your full name: ");
                        name = SCANNER.nextLine().strip();

                        if (name.isBlank()){
                            System.out.printf(ERROR_MSG, "Name Can't be empty!");
                            valid = false;
                            continue;
                        }

                        for (int i = 0; i < name.length(); i++) {
                            if (Character.isSpaceChar(name.charAt(i))){
                                continue;
                            }
                            else if (!(Character.isLetter(name.charAt(i)))) {
                                System.out.printf(ERROR_MSG, "Invalid name!");
                                valid = false;
                                break;
                            }
                        }
                    }while(!valid);
                    String[] newAccountNumber  = new String[accountNumber.length+1];
                    
                    String[] newAccountCreater = new String[accountCreater.length+1];

                    for (int i = 0; i < accountCreater.length; i++) {
                        newAccountNumber[i] = accountNumber[i];
                        newAccountCreater[i] = accountCreater[i];
                                        
                    }
                    newAccountNumber[newAccountNumber.length - 1] = accountNum;
                    newAccountCreater[newAccountCreater.length-1] = name;

                    accountCreater  =newAccountCreater;
                    accountNumber = newAccountNumber;
                    
                    do{
                        valid = true;
                        System.out.println();
                        System.out.print("\tInitial Deposite: ");
                        initDepo = SCANNER.nextDouble();
                        SCANNER.nextLine();

                        if (initDepo<5000){
                            System.out.printf(ERROR_MSG,"Invalid deposition!");
                            valid = false;
                        }
                    }while(!valid);

                    double[] newInitialDeposit = new double[intialDeposit.length+1];
                    double[] newCurrentBalance = new double[currentBalance.length+1];
                    
                    for (int i = 0; i < intialDeposit.length; i++) {
                        newInitialDeposit[i] = intialDeposit[i];
                        newCurrentBalance[i] = currentBalance[i]+intialDeposit[i];
                                        
                    }
                    newInitialDeposit[newInitialDeposit.length - 1] = initDepo;
                    newCurrentBalance[newAccountCreater.length-1] = ;

                    intialDeposit  = newInitialDeposit;
                    currentBalance = newCurrentBalance;


                    System.out.println();
                    System.out.printf(SUCCESS_MSG,String.format("\t\t%sSDB-%05d%s: %s%s%s has been created successfully!\n\n",COLOR_BLUE_BOLD,id,RESET,COLOR_GREEN_BOLD,name,RESET));
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;

                    
                    
                break;
                case DEPOSIT:
                    //Account Number validation
                    int index = 0;
                    double currentBal = 0;
                    do{
                        valid = true;
                        System.out.print("Enter youre account number: ");
                        String acc = SCANNER.nextLine().strip();

                        if(acc.isBlank()){
                            System.out.printf(ERROR_MSG,"Account number cannot be empty");
                            valid = false;
                        }
                        else if (!(acc.startsWith("SDB-"))){
                            System.out.printf(ERROR_MSG,"Invalid account number!Try again.");
                            valid = false;                       
                        }else{
                            acc = acc.substring(4);
                            for (int i = 0; i < acc.length(); i++) {
                                if (!Character.isDigit(acc.charAt(i))){
                                    System.out.printf(ERROR_MSG, "Invalid Account number format!");
                                    valid = false;
                                    break;
                                }

                            }while(!valid);

                            boolean exists = false;
                            for (int i = 0; i < accountNumber.length; i++) {
                                if (accountNumber[i].equals(acc)){
                                    index = i;
                                    exists =true;
                                    break;
                                }
                                
                            }
                            if (!exists){
                                valid = false;
                                System.out.printf(ERROR_MSG, "Account number does not exist");

                            }

                            if(!valid){
                                System.out.print("\n\tDo you want to try again? (Y/n)");
                                if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                    screen = DASHBOARD;
                                    continue mainloop;
                                }

                            }
                        }

                    }while(!valid);
                    System.out.println("Current Balace: ");
                    

                }





        }while(true);

    }
}