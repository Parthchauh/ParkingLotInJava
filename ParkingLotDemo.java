import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;


public class ParkingLotDemo
{
    public static void main(String[] args) 
    {
        int nFloors = 4;
        int nSlotsPerFloor = 6;
        ParkingLot parkingLot = new ParkingLot("PR1234", nFloors, 10, 20, 10);
        Scanner scanner = new Scanner(System.in);

       while (true) 
       {
            System.out.println("Choose an option:");
            System.out.println("1. View parked vehicles");
            System.out.println("2. Park a vehicle");
            System.out.println("3. Display parking availability");
            System.out.println("4. Unpark a vehicle");
            System.out.println("5. Exit");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewParkedVehicles(parkingLot, scanner);
                        break;
                    case 2:
                        parkVehicle(parkingLot, scanner);
                        break;
                    case 3:
                        parkingLot.displayAvailability();
                        break;
                    case 4:
                        unparkVehicle(parkingLot, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose a number between 1 and 5.");
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
       }
}


private static void viewParkedVehicles(ParkingLot parkingLot, Scanner scanner) 
{
    String type;
    while (true) 
    {
        System.out.println("Enter vehicle type (car/truck/bike): ");
        type = scanner.nextLine().toLowerCase(); // Convert input to lowercase
        if (type.equals("car") || type.equals("truck") || type.equals("bike")) {
            break; // Exit loop if input is valid
        } else {
            System.out.println("Invalid vehicle type. Please choose car, truck, or bike.");
        }
        System.out.println();
    }
    
    System.out.println();
    System.out.println("Choose search criteria:");
    System.out.println("1. Search by ticket ID");
    System.out.println("2. Search by registration number");
    System.out.println("3. Display parking availability");
    System.out.println("4. Exit...");
    System.out.println();
    int searchChoice = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    
    switch (searchChoice) 
    {
        case 1:
            searchByTicketId(parkingLot, scanner);
            break;
        case 2:
            searchByRegistrationNumber(parkingLot, scanner);
            break;
        case 3:
            parkingLot.displayAvailability();
            break;
        case 4:
            System.out.println("Exiting...");
            return;
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}

private static void unparkVehicle(ParkingLot parkingLot, Scanner scanner) 
{
    System.out.println("Enter ticket ID or registration number to unpark the vehicle: ");
    String input = scanner.nextLine().toUpperCase(); // Convert input to uppercase for consistency
    
    // Check if the input is a ticket ID or a registration number
    if (input.startsWith(parkingLot.parkingLotId)) {
        // Input is a ticket ID
        boolean success = parkingLot.unparkVehicle(input, scanner); // Pass scanner to handle ticket ID case
        if (success) {
            System.out.println("Vehicle unparked successfully.");
        } else {
            System.out.println("Failed to unpark the vehicle. Invalid ticket ID.");
        }
    } 
    else {
        // Input is a registration number
        boolean success = parkingLot.unparkVehicleByRegistrationNumber(input);
        if (success) {
            System.out.println("Vehicle unparked successfully.");
        } else {
            System.out.println("Failed to unpark the vehicle. Vehicle with this registration number not found.");
        }
    }
    System.out.println(); // Print an empty line for formatting
}

    private static void searchByRegistrationNumber(ParkingLot parkingLot, Scanner scanner) 
    {
    System.out.println("Enter vehicle registration number: ");
    String regNo = scanner.nextLine().toUpperCase(); // Convert to uppercase
    
    // Check if the registration number follows the standard format
    if (!isValidRegistrationNumber(regNo)) {
        System.out.println("Invalid registration number format. Please follow the standard format.");
        return; // Exit method if format is invalid
    }

    Vehicle vehicle = parkingLot.searchByRegistrationNumber(regNo);
    if (vehicle != null) {
        System.out.println(); // Add an empty line
        System.out.println("Vehicle found:");
        System.out.println("Type: " + vehicle.type);
        System.out.println("Registration number: " + vehicle.registration);
        
        // Find the floor and slot of the vehicle
        for (int i = 0; i < parkingLot.slots.size(); i++) {
            List<Slot> floor = parkingLot.slots.get(i);
            for (int j = 0; j < floor.size(); j++) {
                Slot slot = floor.get(j);
                if (slot.vehicle != null && slot.vehicle.registration.equals(regNo)) {
                    System.out.println("Floor: " + (i + 1));
                    System.out.println("Slot: " + (j + 1));
                    break;
                }
            }
        }
    } else {
        System.out.println("Vehicle not found.");
    }
    System.out.println(); // Add an empty line after completion
}

private static boolean isValidRegistrationNumber(String regNo) {
    // Check if the registration number follows the standard format
    // For example: StateName + District Code + Two Random Characters + Four Digits Random
    
    // Check if the length is correct
    if (regNo.length() != 10) {
        return false;
    }

    // Check if the first two characters are alphabetic (State Name)
    if (!Character.isLetter(regNo.charAt(0)) || !Character.isLetter(regNo.charAt(1))) {
        return false;
    }

    // Check if the next two characters are digits (District Code)
    if (!Character.isDigit(regNo.charAt(2)) || !Character.isDigit(regNo.charAt(3))) {
        return false;
    }

    // Add more checks if needed for the remaining characters
    
    return true;
}



    //serach by ticket id
    private static void searchByTicketId(ParkingLot parkingLot, Scanner scanner) {
    System.out.println();
    System.out.println("Enter ticket ID: ");
    String ticketId = scanner.nextLine();

    Vehicle vehicle = parkingLot.searchByTicketId(ticketId);
    if (vehicle != null) {
        System.out.println(); // Add an empty line
        System.out.println();
    } else {
        System.out.println("Vehicle not found.");
        System.out.println();
    }
}
private static void displayAvailability(ParkingLot parkingLot) {
    parkingLot.displayAvailability();
}



   private static void parkVehicle(ParkingLot parkingLot, Scanner scanner) {
    System.out.println();
    System.out.println("Enter vehicle type (car/truck/bike): ");
    String type = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive comparison
    if (!type.equals("car") && !type.equals("truck") && !type.equals("bike")) {
        System.out.println("Please enter a valid vehicle type: car, truck, or bike.");
        return; // Exit method if invalid input
    }
    
    System.out.println("Enter vehicle registration number: ");
    String regNo = scanner.nextLine().toUpperCase(); // Convert to uppercase for consistent formatting
    
    // Check if the registration number follows the standard format
    if (!isValidRegistrationNumber(regNo)) {
        System.out.println("Invalid registration number format. Please follow the standard format like (GJ23AK2030).");
        return; // Exit method if format is invalid
    }
    
    System.out.println("Enter preferred floor:");
    int preferredFloor = scanner.nextInt();
    System.out.println("Enter preferred slot:");
    int preferredSlot = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String ticket = parkingLot.parkVehicle(type, regNo, preferredFloor, preferredSlot);
    
    if (ticket.startsWith("Invalid") || ticket.startsWith("Preferred") || ticket.startsWith("No available")) {
        System.out.println(ticket);
    } else {
        System.out.println("Vehicle parked successfully at Floor " + preferredFloor + ", Slot " + preferredSlot);
        System.out.println("Ticket ID: " + ticket);
    }
    System.out.println();
}


}

