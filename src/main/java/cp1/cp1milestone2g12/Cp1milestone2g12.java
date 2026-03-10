/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cp1.cp1milestone2g12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Computer Programming 1 - H1101
 * Milestone # 2 
 * @author - Group 12
 * MotorPh Automatic Payroll System
 */

public class Cp1milestone2g12 {

      private static final String ATTENDANCE_FILE = "resources/MotorPH_Attendance Record.csv";
      private static final String EMPLOYEE_FILE = "resources/MotorPH_Employee Details.csv";

// =============================
// MAIN METHOD - LOG-IN SYSTEM
// =============================
    public static void main(String[] args) {

Scanner scanner = new Scanner(System.in);

System.out.print("Enter Username: ");
String username = scanner.nextLine();

System.out.print("Enter Password: ");
String password = scanner.nextLine();

if (!(password.equals("12345") &&
        (username.equals("employee") || username.equals("payroll_staff")))) {

    System.out.println("Incorrect username and/or password.");
    scanner.close();
    return;
}

// =============================
// EMPLOYEE ROLE
// =============================
if (username.equals("employee")) {

    System.out.println("Login Successful. Welcome! Employee");

    while (true) {

        System.out.println("\n========= MENU =========");
        System.out.println("\n[1] Enter Your Employee Number");
        System.out.println("[2] Exit Program");
        System.out.print("\nSelect Option: ");

        int choice;

        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            continue;
        }

        switch (choice) {

            case 1:

                System.out.print("Enter Employee Number: ");
                String empNumber = scanner.nextLine().trim();

                if (!empNumber.isEmpty()) {
                    searchEmployee(empNumber);
                } else {
                    System.out.println("Employee Number cannot be empty.");
                }

                break;

            case 2:

                System.out.println("Exiting program...");
                scanner.close();
                return;

            default:

                System.out.println("Invalid option. Please try again.");
        }
    }
}

// =============================
// PAYROLL STAFF ROLE
// =============================
else if (username.equals("payroll_staff")) {

    System.out.println("Login Successful. Welcome! Payroll Staff");

    boolean running = true;

    while (running) {

        System.out.println("\n========= PAYROLL MENU =========");
        System.out.println("\n[1] Process Payroll");
        System.out.println("[2] Exit Program");
        System.out.print("\nSelect Option: ");

        int choice;

        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            continue;
        }

        switch (choice) {

            case 1:

                boolean payrollMenu = true;

                while (payrollMenu) {

                    System.out.println("\n------ Payroll Options ------");
                    System.out.println("\n[1] One Employee");
                    System.out.println("[2] All Employees");
                    System.out.println("[3] Exit");
                    System.out.print("\nSelect Option: ");

                    int option;

                    try {
                        option = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                        continue;
                    }

                    switch (option) {

                        case 1:

                            System.out.print("Enter Employee Number: ");
                            String empNumber = scanner.nextLine();
                            processOneEmployee(empNumber);
                            break;

                        case 2:

                            processAllEmployees();
                            break;

                        case 3:

                            payrollMenu = false;
                            break;

                        default:

                            System.out.println("Invalid option.");
                    }
                }

                break;

            case 2:

                running = false;
                System.out.println("Exiting program...");
                break;

            default:

                System.out.println("Invalid option.");
        }
    }
}

scanner.close();

}

// =========================================================
// READ EMPLOYEE FILE
// =========================================================
private static BufferedReader openEmployeeFile() throws IOException {
File file = new File(EMPLOYEE_FILE);


 if (!file.exists()) {
     throw new IOException("Employee file not found.");
 }

 BufferedReader br = new BufferedReader(new FileReader(file));
 br.readLine(); // Skip header
 return br;


}

// =========================================================
// HELPER -- FIND EMPLOYEE
// =========================================================
private static String[] findEmployee(String empNumber) throws IOException {


 try (BufferedReader br = openEmployeeFile()) {

     String line;

     while ((line = br.readLine()) != null) {

         String[] data = line.split(",");
         if (data.length < 4) continue;

         if (data[0].trim().equals(empNumber)) {
             return data;
         }
     }
 }

 return null;


}

// =========================================================
// EMPLOYEE -- SEARCH EMPLOYEE
// =========================================================
private static void searchEmployee(String empNumber) {


 try {

     String[] data = findEmployee(empNumber);

     if (data != null) {

         printEmployeeInfo(data);

     } else {

         System.out.println("Employee number does not exist.");
     }

 } catch (IOException e) {

     System.out.println(e.getMessage());
 }


}

// =========================================================
// PAYROLL -- PROCESS ONE EMPLOYEE
// =========================================================
private static void processOneEmployee(String empNumber) {


 try {

     String[] data = findEmployee(empNumber);

     if (data != null) {

         printEmployeeInfo(data);

         double hourlyRate = parseHourlyRate(data);

         processAttendance(empNumber, hourlyRate);

     } else {

         System.out.println("Employee number does not exist.");
     }

 } catch (IOException e) {

     System.out.println(e.getMessage());
 }

}

// =========================================================
// PAYROLL -- PROCESS ALL EMPLOYEES
// =========================================================
private static void processAllEmployees() {


 try (BufferedReader br = openEmployeeFile()) {

     String line;

     while ((line = br.readLine()) != null) {

         String[] data = line.split(",");
         if (data.length < 4) continue;

         printEmployeeInfo(data);

         String empNumber = data[0].trim();
         double hourlyRate = parseHourlyRate(data);

         processAttendance(empNumber, hourlyRate);
     }

 } catch (IOException e) {

     System.out.println(e.getMessage());
 }

}

// =========================================================
// HELPER -- PRINT EMPLOYEE INFO
// =========================================================
private static void printEmployeeInfo(String[] data) {


 String employeeNumber = data[0];
 String lastName = data[1];
 String firstName = data[2];
 String birthday = data[3];

 System.out.println("\n=================================");
 System.out.println("Employee Found!");
 System.out.println("\nEmployee #: " + employeeNumber);
 System.out.println("Name: " + lastName + ", " + firstName);
 System.out.println("Birthday: " + birthday);


}

// =========================================================
// HELPER -- PARSE HOURLY RATE
// =========================================================
private static double parseHourlyRate(String[] data) {


 try {

     String value = data[data.length - 1]
             .replace(",", "")
             .replace("\"", "")
             .trim();

     return Double.parseDouble(value);

 } catch (NumberFormatException e) {

     return 0;
 }


}

// =============================
// ATTENDANCE AND PAYROLL PROCESSING
// =============================
    private static void processAttendance(String empNumber, double hourlyRate) {
    
    
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
    File file = new File(ATTENDANCE_FILE);

    if (!file.exists()) {
        System.out.println("Attendance file not found.");
            return;
    }

    int selectedYear = 2024;
    
    //Reset cutoff variables per month
    for (int month = 6; month <= 12; month++) {

        double firstHalf = 0;
        double secondHalf = 0;
        
        int daysInMonth = YearMonth.of(selectedYear, month).lengthOfMonth();
        
        System.out.println("\n=================================");
        System.out.println("Month: " + Month.of(month));
        System.out.println("Year: " + selectedYear);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length < 6) continue;
                if (!data[0].trim().equals(empNumber)) continue;
    
    //Parse attendance date            
    String[] dateParts = data[3].split("/");
        
        if (dateParts.length != 3) continue;

            int recordMonth;
            int day; 
            int year;

                try {
                    recordMonth = Integer.parseInt(dateParts[0]);
                    day = Integer.parseInt(dateParts[1]);
                    year = Integer.parseInt(dateParts[2]);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (year != selectedYear || recordMonth != month)
                    continue;
    
    //Parse login and logout time            
                try {
                    LocalTime login = LocalTime.parse(data[4].trim(), timeFormat);
                    LocalTime logout = LocalTime.parse(data[5].trim(), timeFormat);
    
    //Compute hours worked                
        double hours = computeHours(login, logout);

            if (day <= 15) {       
                firstHalf += hours;
                    } else {
                secondHalf += hours;
                    }

                } catch (Exception e) {
                    continue;
}
            }

        } catch (IOException e) {
            System.out.println("Error reading attendance file.");
        }

// ======================================================
// GROSS SALARY CALCULATION (PER CUTOFF)
// ======================================================
                
        double grossFirst = firstHalf * hourlyRate;
        double grossSecond = secondHalf * hourlyRate;

// ======================================================
// COMBINED MONTHLY GROSS
// ======================================================

        double grossMonthly = grossFirst + grossSecond;

// ======================================================
// MONTHLY DEDUCTIONS
// ======================================================

        double taxMonthly = computeWithholdingTax(grossMonthly);
        double pagibigMonthly = computePagIbig(grossMonthly);
        double philhealthMonthly = computePhilHealth(grossMonthly);
        double sssMonthly = computeSSS(grossMonthly);

        double totalMonthlyDeductions =
                taxMonthly + pagibigMonthly + philhealthMonthly + sssMonthly;

// ======================================================
// MONTHLY NET SALARY
// ======================================================

        double netMonthly = grossMonthly - totalMonthlyDeductions;

        if (netMonthly < 0)
            netMonthly = 0;

// ----------------------------------------------------
// CUTOFF 1 : 1 - 15
// ----------------------------------------------------
        System.out.println("\nCutoff Date: " + Month.of(month) + " 1 to 15");
        System.out.println("Total Hours Worked: " + firstHalf);
        System.out.println("Gross Salary: " + grossFirst);
        System.out.println("Net Salary: " + grossFirst); // No deductions


// ----------------------------------------------------
// CUTOFF 2 : 16 - END OF MONTH
// ----------------------------------------------------
        System.out.println("\nCutoff Date: " + Month.of(month) + " 16 to " + daysInMonth);
        System.out.println("Total Hours Worked: " + secondHalf);
        System.out.println("Gross Salary: " + grossSecond);

        System.out.println("\nEach Deduction:");
        System.out.println("SSS: " + sssMonthly);
        System.out.println("PhilHealth: " + philhealthMonthly);
        System.out.println("Pag-IBIG: " + pagibigMonthly);
        System.out.println("Tax: " + taxMonthly);

      
        System.out.println("\nTotal Deductions: " + totalMonthlyDeductions);
        System.out.println("Net Salary: " + netMonthly);
    }
    }
    
// ======================================================
// HOURS CALCULATION
// ======================================================
    private static double computeHours(LocalTime login, LocalTime logout) {

        if (login == null || logout == null)
            return 0;

        LocalTime startWindow = LocalTime.of(8, 0);
        LocalTime graceLimit = LocalTime.of(8, 10);
        LocalTime endWindow = LocalTime.of(17, 0);

        if (login.isBefore(startWindow)) login = startWindow;
        if (!login.isAfter(graceLimit)) login = startWindow;
        if (logout.isAfter(endWindow)) logout = endWindow;
        if (logout.isBefore(login)) return 0;

        double hoursWorked = Duration.between(login, logout).toMinutes() / 60.0;

        // Calculating unpaid lunch break
        hoursWorked -= 1.0;
            if (hoursWorked < 0)
            hoursWorked = 0;

        return hoursWorked;
    }
        
// =========================
// DEDUCTION METHODS
// =========================
     
    private static double computeWithholdingTax(double gross) {
        if (gross <= 20832) return 0;
        else if (gross < 33333) return (gross - 20833) * 0.20;
        else if (gross < 66667) return 2500 + ((gross - 33333) * 0.25);
        else if (gross < 166667) return 10833 + ((gross - 66667) * 0.30);
        else if (gross < 666667) return 40833.33 + ((gross - 166667) * 0.32);
        else return 200833.33 + ((gross - 666667) * 0.35);
    }

    private static double computePagIbig(double gross) {
        if (gross >= 1000 && gross <= 1500) return gross * 0.01;
        else if (gross > 1500) return gross * 0.02;
        return 0;
    }

    private static double computePhilHealth(double gross) {
        if (gross <= 10000) return 300;
        else if (gross <= 59999.99) return Math.min(gross * 0.03, 1800);
        else return 1800;
    }

    private static double computeSSS(double gross) { 
            if (gross < 3250) return 135.00; 
            else if (gross <= 3750) return 157.50; 
            else if (gross <= 4250) return 180.00; 
            else if (gross <= 4750) return 202.50; 
            else if (gross <= 5250) return 225.00; 
            else if (gross <= 5750) return 247.50; 
            else if (gross <= 6250) return 270.00; 
            else if (gross <= 6750) return 292.50; 
            else if (gross <= 7250) return 315.00; 
            else if (gross <= 7750) return 337.50; 
            else if (gross <= 8250) return 360.00; 
            else if (gross <= 8750) return 382.50; 
            else if (gross <= 9250) return 405.00; 
            else if (gross <= 9750) return 427.50; 
            else if (gross <= 10250) return 450.00;
            else if (gross <= 10750) return 472.50; 
            else if (gross <= 11250) return 495.00; 
            else if (gross <= 11750) return 517.50; 
            else if (gross <= 12250) return 540.00; 
            else if (gross <= 12750) return 562.50; 
            else if (gross <= 13250) return 585.00; 
            else if (gross <= 13750) return 607.50; 
            else if (gross <= 14250) return 630.00; 
            else if (gross <= 14750) return 652.50; 
            else if (gross <= 15250) return 675.00; 
            else if (gross <= 15750) return 697.50; 
            else if (gross <= 16250) return 720.00; 
            else if (gross <= 16750) return 742.50; 
            else if (gross <= 17250) return 765.00; 
            else if (gross <= 17750) return 787.50; 
            else if (gross <= 18250) return 810.00; 
            else if (gross <= 18750) return 832.50; 
            else if (gross <= 19250) return 855.00; 
            else if (gross <= 19750) return 877.50; 
            else if (gross <= 20250) return 900.00; 
            else if (gross <= 20750) return 922.50; 
            else if (gross <= 21250) return 945.00; 
            else if (gross <= 21750) return 967.50; 
            else if (gross <= 22250) return 990.00; 
            else if (gross <= 22750) return 1012.50; 
            else if (gross <= 23250) return 1035.00; 
            else if (gross <= 23750) return 1057.50; 
            else if (gross <= 24250) return 1080.00; 
            else if (gross <= 24750) return 1102.50; 
            else return 1125.00; 
        } 
}

    

