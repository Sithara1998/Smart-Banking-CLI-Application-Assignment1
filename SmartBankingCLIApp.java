
    import java.util.Arrays;
import java.util.Scanner;

    public class SmartBankingCLIApp {
        
            private static final Scanner SCANNER = new Scanner(System.in);

            static final String CLEAR = "\033[H\033[2J";
            static final String COLOR_BLUE_BOLD = "\033[34;1m";
            static final String COLOR_RED_BOLD = "\033[31;1m";
            static final String COLOR_GREEN_BOLD = "\033[32;1m";
            static final String RESET = "\033[0m";

            static final String DASHBOARD = "Welcome to Smart Banking";
            static final String CREATING_ACCOUT = "Open New Account";
            static final String DEPOSIT = "Deposit Money";
            static final String WITHDRAWAL = "Withdraw Money";
            static final String TRANSFER = "Transfer Money";
            static final String CHECK_BALANCE = "Checking Account Balance";
            static final String REMOVE_ACCOUNT = "Drop Exisiting Account";

            static final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
            static final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

            static double newBalance;
            static boolean valid;
            static String name;
            static int id;
            static String accountNum;
            static double initDepo;
            static int index = 0;
            static double depositAmount = 0;
            static String accSub;
            static String acc;
            static double withdrawAmount;
            static String screen;
            static String[][] accountDetails;
        
       
        public static void main(String[] args) {

            // final String CLEAR = "\033[H\033[2J";
            // final String COLOR_BLUE_BOLD = "\033[34;1m";
            // final String COLOR_RED_BOLD = "\033[31;1m";
            // final String COLOR_GREEN_BOLD = "\033[32;1m";
            // final String RESET = "\033[0m";

            // final String DASHBOARD = "Welcome to Smart Banking";
            // final String CREATING_ACCOUT = "Open New Account";
            // final String DEPOSIT = "Deposit Money";
            // final String WITHDRAWAL = "Withdraw Money";
            // final String TRANSFER = "Transfer Money";
            // final String CHECK_BALANCE = "Checking Account Balance";
            // final String REMOVE_ACCOUNT = "Drop Exisiting Account";

            // final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
            // final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

            // double newBalance;
            // boolean valid;
            // String name;
            // int id;
            // String accountNum;
            // double initDepo;
            // int index = 0;
            // double depositAmount = 0;
            // String accSub;
            // String acc;
            // double withdrawAmount;
            // String screen;
            // String[][] accountDetails;

            accountDetails = new String[0][];
            screen = DASHBOARD;

            mainloop: 
            do {
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
                        
                        for (int i = 0; i < accountDetails.length; i++) {
                            tempAccoutDetail[i] = accountDetails[i];
                        }

                        tempAccoutDetail[tempAccoutDetail.length - 1][0] = accountNum;
                        tempAccoutDetail[tempAccoutDetail.length - 1][1] = name;
                        tempAccoutDetail[tempAccoutDetail.length - 1][2] = String.format("%.2f", initDepo);

                        accountDetails = tempAccoutDetail;

                        // for (int i = 0; i < accountDetails.length; i++) {
                        //     System.out.println(Arrays.toString(accountDetails[i]));

                        // }

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
                        index = 0;
                        depositAmount = 0;

                        acValidationDepo1: 
                        do {
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
                                        continue acValidationDepo1;
                                    }
                                }

                                boolean exists = false;
                                for (int i = 0; i < accountDetails.length; i++) {
                                    if (acc.equals(accountDetails[i][0])) {
                                        index = i;
                                        exists = true;
                                        break acValidationDepo1;
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
                        System.out.printf("Current Balace: Rs.%,.2f\n", Double.valueOf(accountDetails[index][2]));
                        System.out.println();
                        
                        depoAmountLoop:
                        do{
                            System.out.print("Deposit Amount: ");
                            depositAmount = SCANNER.nextDouble();
                            SCANNER.nextLine();
                            if (depositAmount<500){
                                System.out.println("Invalid amount!Enter a value above 500");
                                valid = false;
                                continue depoAmountLoop;
                            }
                            newBalance = Double.valueOf(accountDetails[index][2])+depositAmount;
                            System.out.printf("\nNew Balance: Rs.%,.2f\n",newBalance);
                            accountDetails[index][2] = String.format("%.2f",newBalance);
                            System.out.println();
                            
                            System.out.print("\tDo you want to continue (Y/n)? ");
                            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                valid=false;
                                continue;
                            }
                            else{
                                screen = DASHBOARD;
                            }

                        }while(!valid);
                            

                    break;

                    case WITHDRAWAL:
                        // Account Number validation
                        index = 0;
                        withdrawAmount = 0;
                        
                        acValidationDepo2: 
                        do {
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
                                        continue acValidationDepo2;
                                    }
                                }

                                boolean exists = false;
                                for (int i = 0; i < accountDetails.length; i++) {
                                    if (acc.equals(accountDetails[i][0])) {
                                        index = i;
                                        // System.out.println(index);
                                        exists = true;
                                        break acValidationDepo2;
                                    }

                                }
                                if (!exists) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Account number does not exist");
                                    // continue;
                                }

                                if (!valid) {
                                    System.out.print("\n\tDo you want to try again? (Y/n)");
                                    if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                        screen = DASHBOARD;
                                        continue mainloop;
                                    } else {
                                        continue mainloop;
                                    }
                                }
                                System.out.println();
                                
                                break;

                            }
                        } while (!valid);
                        
                        System.out.println();
                        System.out.printf("Current Balace: Rs.%,.2f\n", Double.valueOf(accountDetails[index][2]));
                        System.out.println();
                        
                        // withdrawal Amount Validation
                        withdrawAmountLoop:
                        do{
                            valid = true;
                            System.out.print("Withdrawal Amount: ");
                            withdrawAmount = SCANNER.nextDouble();
                            SCANNER.nextLine();
                            if (Double.valueOf(accountDetails[index][2])<500.0){
                                System.out.println("Your account balance is not valid!");
                                valid = false;
                                continue mainloop;           //
                            }
                            if (withdrawAmount<100){
                                System.out.println("Invalid amount!Enter a value above 500");
                                valid = false;
                                continue withdrawAmountLoop;
                            }
                            newBalance = Double.valueOf(accountDetails[index][2])-withdrawAmount;
                            System.out.printf("\nNew Balance: Rs.%,.2f\n", newBalance);
                            accountDetails[index][2]= String.format("%.2f", newBalance);
                            System.out.println();
                            
                            System.out.print("\tDo you want to continue (Y/n)? ");
                            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                valid = false;
                                continue withdrawAmountLoop;
                            }
                            else{screen = DASHBOARD;}

                        }while(!valid);

                        
                        
                        break;

                    case TRANSFER:
                        // String[][] accountDetail = {
                        //     {"SDB-00001","Sithara","50000.00"}
                        //     ,{"SDB-00002","sadun","12000.00"}
                        //     ,{"SDB-00003","Supun","11200.00"}};
                        // accountDetails = accountDetail;
                    
                        acValidationDepo3: 
                        //From A/C validation
                        do {
                            index = 0;
                            valid = true;
                            System.out.print("Enter From A/C number: ");
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
                                        continue acValidationDepo3;
                                    }
                                }

                                boolean exists = false;
                                for (int i = 0; i < accountDetails.length; i++) {
                                    if (acc.equals(accountDetails[i][0])) {
                                        index = i;
                                        exists = true;
                                        break acValidationDepo3;
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

                        int fromAccIndex = index;
                        String fromAccNumber = acc;
                        System.out.println();
                        acValidationDepo4: 
                        // To A/C validation
                        do { 
                            valid = true;
                            System.out.print("Enter To A/C number: ");
                            acc = SCANNER.nextLine().strip();

                            if (acc.equals(fromAccNumber)){
                                System.out.printf(ERROR_MSG,"Enter another To A/C account");
                                valid = false;
                                continue acValidationDepo4;
                            }

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
                                        continue acValidationDepo4;
                                    }
                                }

                                boolean exists = false;
                                for (int i = 0; i < accountDetails.length; i++) {
                                    if (acc.equals(accountDetails[i][0])) {
                                        index = i;
                                        exists = true;
                                        break acValidationDepo4;
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

                        int toAccIndex = index;
                        String toAccNumber = acc;
                        System.out.println();

                        // final String ACC_HOLDER = 
                        System.out.printf("From A/C holder name: %s%s%s\n",COLOR_BLUE_BOLD,accountDetails[fromAccIndex][1],RESET);
                        System.out.printf("From A/C balance: %sRs%,.2f%s\n",COLOR_GREEN_BOLD,Double.valueOf(accountDetails[fromAccIndex][2]),RESET);
                        System.out.println();
                        System.out.printf("To A/C holder name: %s%s%s\n",COLOR_BLUE_BOLD,accountDetails[toAccIndex][1],RESET);
                        System.out.printf("TO A/C balance: %sRs%,.2f%s\n",COLOR_GREEN_BOLD,Double.valueOf(accountDetails[toAccIndex][2]),RESET);
                        System.out.println();

                        transferValidation:
                        do{
                            valid = true;
                            if (Double.valueOf(accountDetails[fromAccIndex][2])<500){
                                System.out.printf(ERROR_MSG,"Your account balnce is lesser than 500!");
                                valid = false;
                                screen = DASHBOARD;
                                break;
                            }
                            
                            System.out.print("Enter Amount: ");
                            double transferAmount = SCANNER.nextDouble();
                            SCANNER.nextLine();


                            if (transferAmount<100){
                                System.out.printf(ERROR_MSG,"Invalid amount! Enter a value above 100");
                                valid = false;
                                continue transferValidation;
                            }else{
                                double newFromAccBalnce = 0.98*(Double.valueOf(accountDetails[fromAccIndex][2])-transferAmount);
                                System.out.printf("\nNew From A/C balance: %sRs%,.2f%s",COLOR_BLUE_BOLD,newFromAccBalnce,RESET);
                                accountDetails[fromAccIndex][2] = String.format("%.2f",newFromAccBalnce);

                                System.out.println();

                                double newToAccBalnce = Double.valueOf(accountDetails[toAccIndex][2])+transferAmount;
                                System.out.printf("\nNew From A/C balance: %sRs%,.2f%s",COLOR_BLUE_BOLD,newToAccBalnce,RESET);
                                accountDetails[toAccIndex][2] = String.format("%.2f",newToAccBalnce);
                            }

                            System.out.print("\n\tDo you want to continue? (Y/n)");
                            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                continue mainloop;
                                    
                            } else {
                                screen = DASHBOARD;
                            }    


                        }while (!valid);
                        break;
                                        
                    case CHECK_BALANCE:
                        index = 0;
                        
                        acValidationDepo2: 
                        do {
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
                                        continue acValidationDepo2;
                                    }
                                }

                                boolean exists = false;
                                for (int i = 0; i < accountDetails.length; i++) {
                                    if (acc.equals(accountDetails[i][0])) {
                                        index = i;
                                        exists = true;
                                        break acValidationDepo2;
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

                        System.out.printf("A/C Holder Name: %s%s%s",COLOR_BLUE_BOLD,accountDetails[index][1],RESET);
                        System.out.println();
                        System.out.printf("Current A/C Balance: %sRs.%,.2f%s",COLOR_BLUE_BOLD,Double.valueOf(accountDetails[index][2]),RESET);
                        System.out.println();
                        Double withdrawable  = Double.valueOf(accountDetails[index][2])- 500;
                        System.out.printf("Available Balance to Withdraw: %sRs.%,.2f%s",COLOR_BLUE_BOLD,withdrawable,RESET);
                        System.out.println();
                        
                        System.out.print("\n\tDo you want to try again? (Y/n)");
                        if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                            screen = DASHBOARD;
                        } else {
                            continue mainloop;
                        }
                        break;


                    case REMOVE_ACCOUNT:
                    // Id validation
                        index = 0;
                        acValidationDepo5:
                        do {
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
                                        continue acValidationDepo5;
                                    }
                                }

                                boolean exists = false;
                                for (int i = 0; i < accountDetails.length; i++) {
                                    if (acc.equals(accountDetails[i][0])) {
                                        index = i;
                                        exists = true;
                                        break acValidationDepo5;
                                    }

                                }
                                if (!exists) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Account number does not exist");
                                    // continue;
                                }

                                if (!valid) {
                                    System.out.print("\n\tDo you want to try again? (Y/n)");
                                    if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                        screen = DASHBOARD;
                                        continue mainloop;
                                        // break;
                                    } else {
                                        continue mainloop;
                                    }
                                }
                                System.out.println();
                                
                                break;

                            }
                        } while (!valid);
                        System.out.printf("A/C Holder Name: %s%s%s",COLOR_BLUE_BOLD,accountDetails[index][1],RESET);
                        System.out.println();
                        System.out.printf("Current A/C Balance: %sRs.%,.2f%s",COLOR_BLUE_BOLD,Double.valueOf(accountDetails[index][2]),RESET);
                        System.out.println();



                        System.out.print("\n\tAre you sure to delete (Y/n)?");
                        // System.out.println(index);
                        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                            tempAccoutDetail = new String[accountDetails.length-1][];

                            for (int i = 0; i < accountDetails.length; i++) {
                                if (index>i){
                                    tempAccoutDetail[i]=accountDetails[i];

                                }else if(i==index){
                                    continue;
                                }
                                else{
                                    tempAccoutDetail[i-1]=accountDetails[i];
                                }

                            }

                            accountDetails=tempAccoutDetail;
                            System.out.println("Delete is successful");
                            for (int i = 0; i < accountDetails.length; i++) {
                                System.out.println(Arrays.toString(accountDetails[i]));
                            }
                            System.out.print("\n\tDo you want to try again? (Y/n)");
                            if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                                screen = DASHBOARD;
                            } else {
                                continue mainloop;
                            }

                        } else {
                            screen=DASHBOARD;
                        }
                    break;
                    default:
                        continue;
                }
     
            } while (true);

        }


        // valid
        // acc
        // ERROR_MSG
        // accSub
        // accountDetails
        // index
        // screen
        // DASHBOARD;

        // public static void getAccoutNum(String input){
                        // acValidationDepo1:
                        // do{
                        //     valid = true;
                        //     System.out.print("Enter your account number: ");
                        //     acc = SCANNER.nextLine().strip();

                        //     if (acc.isBlank()) {
                        //         System.out.printf(ERROR_MSG, "Account number cannot be empty!");
                        //         valid = false;
                        //         continue;
                        //     }
                        //     if (!(acc.startsWith("SDB-"))) {
                        //         System.out.printf(ERROR_MSG, "Invalid account number!Try again.");
                        //         valid = false;
                        //         continue;
                        //     } else {
                        //         accSub = acc.substring(5);
                        //         for (int i = 0; i < accSub.length(); i++) {
                        //             if (!Character.isDigit(accSub.charAt(i))) {
                        //                 System.out.printf(ERROR_MSG, "Invalid Account number format!");
                        //                 valid = false;
                        //                 continue acValidationDepo1;
                        //             }
                        //         }

                        //         boolean exists = false;
                        //         for (int i = 0; i < accountDetails.length; i++) {
                        //             if (acc.equals(accountDetails[i][0])) {
                        //                 index = i;
                        //                 // System.out.println(index);
                        //                 exists = true;
                        //                 break acValidationDepo1;
                        //             }

                        //         }
                        //         if (!exists) {
                        //             valid = false;
                        //             System.out.printf(ERROR_MSG, "Account number does not exist");
                        //             continue;
                        //         }

                        //         if (!valid) {
                        //             System.out.print("\n\tDo you want to try again? (Y/n)");
                        //             if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                        //                 screen = DASHBOARD;
                        //             } else {
                        //                 continue mainloop;
                        //             }
                        //         }
                        //         System.out.println();
                        //         break;

                        //     }
                        // } while (!valid);
        // }



        
    }