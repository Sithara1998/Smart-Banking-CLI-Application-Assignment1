import java.util.Scanner;
import java.util.Arrays;
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
            static final String REMOVE_ACCOUNT = "Drop Existing Account";

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
                        System.out.println("\t[5]. Check Account Balance");
                        System.out.println("\t[6]. Drop Existing Account");
                        System.out.println("\t[7]. Exit");
                        System.out.println();
                        System.out.print("\tEnter an option to continue: ");
                        int option = SCANNER.nextInt();
                        SCANNER.nextLine();

                        switch (option) {
                            case 1:screen = CREATING_ACCOUT;
                                break;
                            case 2:screen = DEPOSIT;
                                break;
                            case 3:screen = WITHDRAWAL;
                                break;
                            case 4:screen = TRANSFER;
                                break;
                            case 5:screen = CHECK_BALANCE;
                                break;
                            case 6:screen = REMOVE_ACCOUNT;
                                break;
                            case 7:
                                System.out.println(CLEAR);
                                System.exit(0);
                            default:continue;
                        }
                        break;
                    // Opening new account
                    case CREATING_ACCOUT:
                        do {
                            id = accountDetails.length + 1;
                            accountNum = String.format("SDB-%05d", id);
                            System.out.printf("\tID: %s \n\n", accountNum);

                            valid = true;
                            System.out.print("\tEnter your full name: ");
                            name = SCANNER.nextLine().strip();

                            if (name.isBlank()) {
                                System.out.printf(ERROR_MSG, "Name Can't be empty!");
                                valid = false;
                                continue;
                            }

                            for (int i = 0; i < name.length(); i++) {
                                if (Character.isSpaceChar(name.charAt(i))) continue;
                                else if (!(Character.isLetter(name.charAt(i)))) {
                                    System.out.printf(ERROR_MSG, "Invalid name!\n");
                                    valid = false;
                                    break;
                                }
                            }
                        } while (!valid);

                        do {
                            valid = true;
                            System.out.print("\n\tInitial Deposite: ");
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

                        System.out.printf(SUCCESS_MSG,
                                String.format("\n\t\t%sSDB-%05d%s: %s%s%s has been created successfully!\n",
                                        COLOR_BLUE_BOLD, id, RESET, COLOR_GREEN_BOLD, name, RESET));

                        System.out.print("\tDo you want to continue adding (Y/n)? ");
                        if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
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
                            accountNum = getAccountNum();

                            valid = checkAcValidation(accountNum); 
                            if(!valid)continue;
                            else{
                                valid = checkAcSubString(accountNum);
                                if(!valid)continue acValidationDepo1;
                                                              
                                boolean exists = false;
                                exists = existAcNumber(accountNum,accountDetails);
                                if (exists)break acValidationDepo1;
                                else if (!exists) {
                                    valid = false;
                                    acNotExist();
                                    continue;
                                }

                                if (!valid) {
                                    System.out.print("\n\tDo you want to try again? (Y/n)");
                                    if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
                                    continue mainloop;  
                                }
                                System.out.println();
                                break;
                            }
                        } while (!valid);

                        index = getAcIndex(accountNum,accountDetails);
                        
                        System.out.printf("\nCurrent Balace: Rs.%,.2f\n", Double.valueOf(accountDetails[index][2])); 
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
                            accountNum = getAccountNum();

                            valid = checkAcValidation(accountNum); 
                            if(!valid)continue;
                            else{
                                valid = checkAcSubString(accountNum);
                                if(!valid)continue acValidationDepo2;
                                                              
                                boolean exists = false;
                                exists = existAcNumber(accountNum,accountDetails);
                                if (exists)break acValidationDepo2;
                                else if (!exists) {
                                    valid = false;
                                    acNotExist();
                                    continue;
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

                        index = getAcIndex(accountNum,accountDetails);
                        
                        System.out.printf("\nCurrent Balace: Rs.%,.2f\n\n", Double.valueOf(accountDetails[index][2]));
                        
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
                                continue mainloop;           
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
                        acValidationDepo3: 
                        //From A/C validation
                        do {
                            valid = true;
                            accountNum = getAccountNum();

                            valid = checkAcValidation(accountNum); 
                            if(!valid)continue;
                            else{
                                valid = checkAcSubString(accountNum);
                                if(!valid)continue acValidationDepo3;
                                                              
                                boolean exists = false;
                                exists = existAcNumber(accountNum,accountDetails);
                                if (exists)break acValidationDepo3;
                                else if (!exists) {
                                    valid = false;
                                    acNotExist();
                                    continue;
                                }

                                if (!valid) {
                                    System.out.print("\n\tDo you want to try again? (Y/n)");
                                    if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
                                    continue mainloop;
                                }
                                System.out.println();                                
                                break;
                            }
                        } while (!valid);

                        int fromAccIndex = getAcIndex(accountNum,accountDetails);
                        System.out.println();

                        acValidationDepo4: 
                        // To A/C validation
                        do {
                            valid = true;
                            accountNum = getAccountNum();

                            valid = checkAcValidation(accountNum); 
                            if(!valid)continue;
                            else{
                                valid = checkAcSubString(accountNum);
                                if(!valid)continue acValidationDepo4;
                                                              
                                boolean exists = false;
                                exists = existAcNumber(accountNum,accountDetails);
                                if (exists)break acValidationDepo4;
                                else if (!exists) {
                                    valid = false;
                                    acNotExist();
                                    continue;
                                }

                                if (!valid) {
                                    System.out.print("\n\tDo you want to try again? (Y/n)");
                                    if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
                                    continue mainloop;
                                }
                                System.out.println();
                                break;
                            }
                        } while (!valid);

                        int toAccIndex = getAcIndex(accountNum,accountDetails);
                        System.out.println();

                        // final String ACC_HOLDER = 
                        System.out.printf("From A/C holder name: %s%s%s\n",COLOR_BLUE_BOLD,accountDetails[fromAccIndex][1],RESET);
                        System.out.printf("From A/C balance: %sRs%,.2f%s\n\n",COLOR_GREEN_BOLD,Double.valueOf(accountDetails[fromAccIndex][2]),RESET);
                        System.out.printf("To A/C holder name: %s%s%s\n",COLOR_BLUE_BOLD,accountDetails[toAccIndex][1],RESET);
                        System.out.printf("TO A/C balance: %sRs%,.2f%s\n\n",COLOR_GREEN_BOLD,Double.valueOf(accountDetails[toAccIndex][2]),RESET);
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
                            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue mainloop; 
                            screen = DASHBOARD;   
                        }while (!valid); 
                        break;
                                        
                    case CHECK_BALANCE:
                        index = 0;
                        acValidationDepo6: 
                        do {
                            valid = true;
                            accountNum = getAccountNum();

                            valid = checkAcValidation(accountNum); 
                            if(!valid)continue;
                            else{
                                valid = checkAcSubString(accountNum);
                                if(!valid)continue acValidationDepo6;
                                                              
                                boolean exists = false;
                                exists = existAcNumber(accountNum,accountDetails);
                                if (exists)break acValidationDepo6;
                                else if (!exists) {
                                    valid = false;
                                    acNotExist();
                                    continue;
                                }

                                if (!valid) {
                                    System.out.print("\n\tDo you want to try again? (Y/n)");
                                    if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
                                    continue mainloop;
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
                        if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
                        else {
                            continue mainloop;
                        }
                        break;


                    case REMOVE_ACCOUNT:
                    // Id validation
                        index = 0;
                        acValidationDepo5:
                        do {
                            valid = true;
                            accountNum = getAccountNum();

                            valid = checkAcValidation(accountNum); 
                            if(!valid)continue;
                            else{
                                valid = checkAcSubString(accountNum);
                                if(!valid)continue acValidationDepo5;
                                                              
                                boolean exists = false;
                                exists = existAcNumber(accountNum,accountDetails);
                                if (exists)break acValidationDepo5;
                                else if (!exists) {
                                    valid = false;
                                    acNotExist();
                                    continue;
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
                        System.out.printf("A/C Holder Name: %s%s%s\n",COLOR_BLUE_BOLD,accountDetails[index][1],RESET);
                        System.out.printf("Current A/C Balance: %sRs.%,.2f%s\n",COLOR_BLUE_BOLD,Double.valueOf(accountDetails[index][2]),RESET);



                        System.out.print("\n\tAre you sure to delete (Y/n)?");
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
                    default:continue;
                }
            } while (true);

        }

        public static String getAccountNum(){
            System.out.print("Enter your account number: ");
            String accountNum = SCANNER.nextLine().strip();
            return accountNum;
        }

        public static boolean checkAcValidation(String accoutNumber){
            if (accoutNumber.isBlank()) {
                System.out.printf(getErrorMsg(), "Account number cannot be empty!");
                valid = false;                   
            }
            else if (!(accoutNumber.startsWith("SDB-"))) {
                System.out.printf(getErrorMsg(), "Invalid account number!Try again.");
                valid = false;
                }
            else if (!(accoutNumber.startsWith("SDB-"))) {
                System.out.printf(getErrorMsg(), "Invalid account number!Try again.");
                valid = false;
                }
            return valid;
        }

        public static boolean checkAcSubString(String accoutNumber){
            accoutNumber = accoutNumber.substring(5);
            for (int i = 0; i < accoutNumber.length(); i++) {
                if (!Character.isDigit(accoutNumber.charAt(i))) {
                    System.out.printf(getErrorMsg(), "Invalid Account number format!");
                    valid = false;
                    break;
                }
            }
            return valid;
        }

        public static Boolean existAcNumber(String accoutNumber,String[][] accountDetails){
            boolean exists = false;
            int index =0;
            for (int i = 0; i < accountDetails.length; i++) {
                if (accoutNumber.equals(accountDetails[i][0])) {
                    index = i;                                
                    exists = true;
                    break;
                }
            }
            return exists;
        }
        
        public static Integer getAcIndex(String accoutNumber,String[][] accountDetails){
            boolean exists = false;
            int index =0;
            for (int i = 0; i < accountDetails.length; i++) {
                if (accoutNumber.equals(accountDetails[i][0])) {
                    index = i;                                
                    exists = true;
                    break;
                }
            }
            return index;
        }
        
        public static void acNotExist( ){
                System.out.printf(getErrorMsg(), "Account number does not exist");
            }

        public static String getErrorMsg(){
            final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
            return ERROR_MSG;
        }

        public static String getSuccessMsg(){
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);
            return SUCCESS_MSG;
        }   
    }