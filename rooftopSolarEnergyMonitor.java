import java.util.ArrayList;
import java.util.Scanner;

// Class to store one day's solar energy information
class SolarRecord {

    private String date;
    private double solarGenerated;
    private double energyConsumed;
    private double batteryCharged;
    private double batteryDischarged;
    private double gridExport;
    private double gridImport;

    // Constructor
    public SolarRecord(String date,
                       double solarGenerated,
                       double energyConsumed,
                       double batteryCharged,
                       double batteryDischarged,
                       double gridExport,
                       double gridImport) {

        this.date = date;
        this.solarGenerated = solarGenerated;
        this.energyConsumed = energyConsumed;
        this.batteryCharged = batteryCharged;
        this.batteryDischarged = batteryDischarged;
        this.gridExport = gridExport;
        this.gridImport = gridImport;
    }

    // Getter methods
    public String getDate() {
        return date;
    }

    public double getSolarGenerated() {
        return solarGenerated;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public double getBatteryCharged() {
        return batteryCharged;
    }

    public double getBatteryDischarged() {
        return batteryDischarged;
    }

    public double getGridExport() {
        return gridExport;
    }

    public double getGridImport() {
        return gridImport;
    }

    // Display one record
    public void displayRecord() {

        System.out.println("--------------------------------------------");

        System.out.println("Date               : " + date);

        System.out.printf(
                "Solar Generated    : %.2f kWh%n",
                solarGenerated
        );

        System.out.printf(
                "Energy Consumed    : %.2f kWh%n",
                energyConsumed
        );

        System.out.printf(
                "Battery Charged    : %.2f kWh%n",
                batteryCharged
        );

        System.out.printf(
                "Battery Discharged : %.2f kWh%n",
                batteryDischarged
        );

        System.out.printf(
                "Grid Export        : %.2f kWh%n",
                gridExport
        );

        System.out.printf(
                "Grid Import        : %.2f kWh%n",
                gridImport
        );

        System.out.println("--------------------------------------------");
    }
}


// Main class
public class rooftopSolarEnergyMonitor {

    static Scanner scanner = new Scanner(System.in);

    // Store all daily records
    static ArrayList<SolarRecord> records =
            new ArrayList<>();

    // Battery details
    static double batteryCapacity = 10.0;
    static double batteryLevel = 0.0;

    // Electricity rates
    static double electricityRate = 8.0;
    static double exportRate = 3.0;


    // --------------------------------------------------
    // ADD DAILY ENERGY RECORD
    // --------------------------------------------------

    public static void addDailyRecord() {

        scanner.nextLine();

        System.out.print("Enter date (DD-MM-YYYY): ");
        String date = scanner.nextLine();

        System.out.print("Enter solar energy generated (kWh): ");
        double solarGenerated = scanner.nextDouble();

        System.out.print("Enter household energy consumption (kWh): ");
        double energyConsumed = scanner.nextDouble();

        // Validate input
        if (solarGenerated < 0 || energyConsumed < 0) {

            System.out.println(
                    "Energy values cannot be negative."
            );

            return;
        }

        double batteryCharged = 0;
        double batteryDischarged = 0;
        double gridExport = 0;
        double gridImport = 0;


        // --------------------------------------------------
        // CASE 1: SOLAR GENERATION IS GREATER THAN CONSUMPTION
        // --------------------------------------------------

        if (solarGenerated >= energyConsumed) {

            double surplus =
                    solarGenerated - energyConsumed;

            // Available space in battery
            double availableBatterySpace =
                    batteryCapacity - batteryLevel;

            // Charge battery
            batteryCharged =
                    Math.min(
                            surplus,
                            availableBatterySpace
                    );

            batteryLevel += batteryCharged;

            // Remaining energy goes to grid
            gridExport =
                    surplus - batteryCharged;

        }


        // --------------------------------------------------
        // CASE 2: CONSUMPTION IS GREATER THAN SOLAR GENERATION
        // --------------------------------------------------

        else {

            double energyDeficit =
                    energyConsumed - solarGenerated;

            // Use battery first
            batteryDischarged =
                    Math.min(
                            energyDeficit,
                            batteryLevel
                    );

            batteryLevel -= batteryDischarged;

            // Remaining requirement comes from grid
            gridImport =
                    energyDeficit - batteryDischarged;
        }


        // Create a SolarRecord object
        SolarRecord record =
                new SolarRecord(
                        date,
                        solarGenerated,
                        energyConsumed,
                        batteryCharged,
                        batteryDischarged,
                        gridExport,
                        gridImport
                );


        // Store record
        records.add(record);


        // Display result
        System.out.println(
                "\nDaily energy record added successfully!"
        );

        System.out.printf(
                "Battery Charged    : %.2f kWh%n",
                batteryCharged
        );

        System.out.printf(
                "Battery Discharged : %.2f kWh%n",
                batteryDischarged
        );

        System.out.printf(
                "Grid Export        : %.2f kWh%n",
                gridExport
        );

        System.out.printf(
                "Grid Import        : %.2f kWh%n",
                gridImport
        );

        System.out.printf(
                "Battery Level      : %.2f kWh%n",
                batteryLevel
        );
    }


    // --------------------------------------------------
    // DISPLAY ALL RECORDS
    // --------------------------------------------------

    public static void displayAllRecords() {

        if (records.isEmpty()) {

            System.out.println(
                    "\nNo energy records available."
            );

            return;
        }

        System.out.println(
                "\n========== DAILY SOLAR RECORDS =========="
        );

        for (SolarRecord record : records) {

            record.displayRecord();
        }
    }


    // --------------------------------------------------
    // DISPLAY BATTERY STATUS
    // --------------------------------------------------

    public static void displayBatteryStatus() {

        System.out.println(
                "\n========== BATTERY STATUS =========="
        );

        System.out.printf(
                "Battery Capacity : %.2f kWh%n",
                batteryCapacity
        );

        System.out.printf(
                "Current Battery  : %.2f kWh%n",
                batteryLevel
        );

        double percentage =
                (batteryLevel / batteryCapacity) * 100;

        System.out.printf(
                "Battery Level    : %.2f%%%n",
                percentage
        );


        if (percentage >= 80) {

            System.out.println(
                    "Status           : HIGH"
            );

        } else if (percentage >= 40) {

            System.out.println(
                    "Status           : MEDIUM"
            );

        } else if (percentage > 0) {

            System.out.println(
                    "Status           : LOW"
            );

        } else {

            System.out.println(
                    "Status           : EMPTY"
            );
        }
    }


    // --------------------------------------------------
    // DISPLAY ENERGY STATUS
    // --------------------------------------------------

    public static void energyStatus() {

        if (records.isEmpty()) {

            System.out.println(
                    "\nNo energy data available."
            );

            return;
        }

        // Get latest record
        SolarRecord latest =
                records.get(records.size() - 1);

        double generated =
                latest.getSolarGenerated();

        double consumed =
                latest.getEnergyConsumed();


        System.out.println(
                "\n========== ENERGY STATUS =========="
        );

        System.out.printf(
                "Today's Generation : %.2f kWh%n",
                generated
        );

        System.out.printf(
                "Today's Consumption: %.2f kWh%n",
                consumed
        );


        if (generated > consumed) {

            double surplus =
                    generated - consumed;

            System.out.printf(
                    "Energy Status      : SURPLUS %.2f kWh%n",
                    surplus
            );

        } else if (generated < consumed) {

            double deficit =
                    consumed - generated;

            System.out.printf(
                    "Energy Status      : DEFICIT %.2f kWh%n",
                    deficit
            );

        } else {

            System.out.println(
                    "Energy Status      : BALANCED"
            );
        }

        System.out.println(
                "==================================="
        );
    }


    // --------------------------------------------------
    // DISPLAY OVERALL SUMMARY
    // --------------------------------------------------

    public static void displaySummary() {

        if (records.isEmpty()) {

            System.out.println(
                    "\nNo data available for summary."
            );

            return;
        }


        double totalSolar = 0;
        double totalConsumption = 0;
        double totalBatteryCharge = 0;
        double totalBatteryDischarge = 0;
        double totalExport = 0;
        double totalImport = 0;


        // Calculate totals
        for (SolarRecord record : records) {

            totalSolar +=
                    record.getSolarGenerated();

            totalConsumption +=
                    record.getEnergyConsumed();

            totalBatteryCharge +=
                    record.getBatteryCharged();

            totalBatteryDischarge +=
                    record.getBatteryDischarged();

            totalExport +=
                    record.getGridExport();

            totalImport +=
                    record.getGridImport();
        }


        // Direct solar usage
        double solarUsedDirectly =
                totalSolar
                - totalBatteryCharge
                - totalExport;


        // Estimated savings
        double estimatedSavings =
                (solarUsedDirectly
                        + totalBatteryDischarge)
                * electricityRate;


        // Income from exporting electricity
        double exportIncome =
                totalExport * exportRate;


        double totalSavings =
                estimatedSavings + exportIncome;


        // Display summary
        System.out.println(
                "\n========== SOLAR ENERGY SUMMARY =========="
        );

        System.out.printf(
                "Total Solar Generated : %.2f kWh%n",
                totalSolar
        );

        System.out.printf(
                "Total Consumption     : %.2f kWh%n",
                totalConsumption
        );

        System.out.printf(
                "Direct Solar Usage    : %.2f kWh%n",
                solarUsedDirectly
        );

        System.out.printf(
                "Battery Charged       : %.2f kWh%n",
                totalBatteryCharge
        );

        System.out.printf(
                "Battery Discharged    : %.2f kWh%n",
                totalBatteryDischarge
        );

        System.out.printf(
                "Grid Export           : %.2f kWh%n",
                totalExport
        );

        System.out.printf(
                "Grid Import           : %.2f kWh%n",
                totalImport
        );

        System.out.printf(
                "Estimated Energy Save : Rs. %.2f%n",
                estimatedSavings
        );

        System.out.printf(
                "Grid Export Income    : Rs. %.2f%n",
                exportIncome
        );

        System.out.printf(
                "Total Estimated Save  : Rs. %.2f%n",
                totalSavings
        );

        System.out.println(
                "=========================================="
        );
    }


    // --------------------------------------------------
    // UPDATE ELECTRICITY RATES
    // --------------------------------------------------

    public static void updateRates() {

        System.out.println(
                "\n========== RATE SETTINGS =========="
        );

        System.out.printf(
                "Current Electricity Rate : Rs. %.2f/kWh%n",
                electricityRate
        );

        System.out.printf(
                "Current Export Rate      : Rs. %.2f/kWh%n",
                exportRate
        );


        System.out.print(
                "\nEnter new electricity rate: "
        );

        electricityRate =
                scanner.nextDouble();


        System.out.print(
                "Enter new grid export rate: "
        );

        exportRate =
                scanner.nextDouble();


        System.out.println(
                "\nRates updated successfully."
        );
    }


    // --------------------------------------------------
    // MAIN METHOD
    // --------------------------------------------------

    public static void main(String[] args) {

        int choice;


        System.out.println(
                "=============================================="
        );

        System.out.println(
                "       ROOFTOP SOLAR ENERGY MONITOR"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Solar Generation Monitoring System"
        );

        System.out.println(
                "=============================================="
        );


        do {

            System.out.println(
                    "\n--------------- MAIN MENU ----------------"
            );

            System.out.println(
                    "1. Add Daily Energy Record"
            );

            System.out.println(
                    "2. View All Energy Records"
            );

            System.out.println(
                    "3. View Battery Status"
            );

            System.out.println(
                    "4. View Energy Status"
            );

            System.out.println(
                    "5. View Overall Summary"
            );

            System.out.println(
                    "6. Update Electricity Rates"
            );

            System.out.println(
                    "7. Exit"
            );


            System.out.print(
                    "\nEnter your choice: "
            );

            choice =
                    scanner.nextInt();


            switch (choice) {

                case 1:
                    addDailyRecord();
                    break;

                case 2:
                    displayAllRecords();
                    break;

                case 3:
                    displayBatteryStatus();
                    break;

                case 4:
                    energyStatus();
                    break;

                case 5:
                    displaySummary();
                    break;

                case 6:
                    updateRates();
                    break;

                case 7:

                    System.out.println(
                            "\nThank you for using "
                            + "Rooftop Solar Energy Monitor!"
                    );

                    break;

                default:

                    System.out.println(
                            "\nInvalid choice. Please try again."
                    );
            }

        } while (choice != 7);


        scanner.close();
    }
}
