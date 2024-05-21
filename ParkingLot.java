import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Import DateTimeFormatter
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class Slot {
    String type;
    Vehicle vehicle;
    String ticketId;
    LocalDateTime entryTime;
    boolean isReserved; // Added variable

    // Default constructor
    public Slot() {
        this.type = null;
        this.vehicle = null;
        this.ticketId = null;
        this.entryTime = null;
        this.isReserved = false; // Initialize to false
    }

    // Constructor with type argument
    public Slot(String type) {
        this.type = type;
        this.vehicle = null;
        this.ticketId = null;
        this.entryTime = null;
        this.isReserved = false; // Initialize to false
    }

    // Method to calculate parking charge
    double calculateParkingCharge() {
        // Define your calculation logic here
        // For example:
        return 10.0; // Default return value
    }
}

class Vehicle {
    String type;
    String registration;
    String id; // Added variable
    String licensePlate; // Added variable

    public Vehicle(String type, String registration) {
        this.type = type;
        this.registration = registration;
        this.id = registration; // Assuming registration is the vehicle ID
        this.licensePlate = registration; // Assuming registration is the license plate
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
public class ParkingLot {
    static final int MAX_FLOORS = 10; // Define the maximum number of floors
    static final int SLOTS_PER_FLOOR = 10;
    String parkingLotId;
    List<List<Slot>> slots;

    int availableCarSlots;
    int availableTruckSlots;
    int availableBikeSlots;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // Constructor
    public ParkingLot(int floors, int slotsPerFloor) {
        slots = new ArrayList<>();
        for (int i = 0; i < floors; i++) {
            ArrayList<Slot> floorSlots = new ArrayList<>();
            for (int j = 0; j < slotsPerFloor; j++) {
                floorSlots.add(new Slot());
            }
            slots.add(floorSlots);
        }
    }
    ParkingLot(String parkingLotId, int nFloors, int carSlotsPerFloor, int truckSlotsPerFloor, int bikeSlotsPerFloor) {
        this.parkingLotId = parkingLotId;
        slots = new ArrayList<>();
        availableCarSlots = nFloors * carSlotsPerFloor;
        availableTruckSlots = nFloors * truckSlotsPerFloor;
        availableBikeSlots = nFloors * bikeSlotsPerFloor;

        for (int i = 0; i < nFloors; i++) {
            List<Slot> floor = new ArrayList<>();
            for (int j = 0; j < SLOTS_PER_FLOOR; j++) {
                if (j < carSlotsPerFloor) {
                    floor.add(new Slot("car"));
                } else if (j < carSlotsPerFloor + truckSlotsPerFloor) {
                    floor.add(new Slot("truck"));
                } else {
                    floor.add(new Slot("bike"));
                }
            }
            slots.add(floor);
        }
    }

    void writeToLogFile(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("parking_log.txt", true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   public void findVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter vehicle ID or license plate: ");
        String vehicleId = scanner.nextLine();

        for (int floor = 0; floor < slots.size(); floor++) {
            for (int slotNum = 0; slotNum < slots.get(floor).size(); slotNum++) {
                Slot slot = slots.get(floor).get(slotNum);
                if (slot.vehicle != null && (slot.vehicle.getId().equals(vehicleId) || slot.vehicle.getLicensePlate().equals(vehicleId))) {
                    System.out.printf("Vehicle found on Floor %d, Slot %d%n", floor + 1, slotNum + 1);
                    System.out.println();//empty
                    return;
                }
            }
        }
        System.out.println("Vehicle not found.");
        System.out.println();//space
    }
public void reserveSlot() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter floor number: ");
    int floor = scanner.nextInt() - 1;
    System.out.print("Enter slot number: ");
    int slotNum = scanner.nextInt() - 1;

    if (slots.get(floor).get(slotNum).isReserved) {
        System.out.println("Slot is already reserved.");
        System.out.println();
    } else {
        slots.get(floor).get(slotNum).isReserved = true;
        System.out.println("Slot reserved successfully.");
        System.out.println();
    }
}
public void releaseReservedSlot() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter floor number: ");
    int floor = scanner.nextInt() - 1;
    System.out.print("Enter slot number: ");
    int slotNum = scanner.nextInt() - 1;

    if (!slots.get(floor).get(slotNum).isReserved) {
        System.out.println("Slot is not reserved.");
    } else {
        slots.get(floor).get(slotNum).isReserved = false;
        System.out.println("Reserved slot released successfully.");
    }
}
public void viewReservationStatus() {
    // Find the maximum width of each column
    int[] columnWidths = new int[slots.get(0).size() + 1]; // +1 for the "Floor" column
    for (int floor = 0; floor < slots.size(); floor++) {
        for (int slotNum = 0; slotNum < slots.get(floor).size(); slotNum++) {
            Slot slot = slots.get(floor).get(slotNum);
            String status = slot.isReserved ? "Reserved" : "Available";
            if (status.length() > columnWidths[slotNum + 1]) {
                columnWidths[slotNum + 1] = status.length();
            }
        }
    }

    System.out.println("+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+");
    System.out.println("| Floor     | Slot 1    | Slot 2    | Slot 3    | Slot 4    | Slot 5    | Slot 6    | Slot 7    | Slot 8    | Slot 9    | Slot 10   |");
    System.out.println("+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+");

    // Print each floor and its slots
    for (int floor = 0; floor < slots.size(); floor++) {
        System.out.print("+--------+");
        for (int i = 0; i < columnWidths.length; i++) {
            System.out.print(String.format("%-" + (columnWidths[i] + 2) + "s+", "").replace(' ', '-'));
        }
        System.out.println();
        System.out.printf("| Floor %-3d |", floor + 1);
        for (int slotNum = 0; slotNum < slots.get(floor).size(); slotNum++) {
            Slot slot = slots.get(floor).get(slotNum);
            String status = slot.isReserved ? "Res" : "Ava";
            System.out.printf(" %-"+columnWidths[slotNum + 1]+"s |", status);
        }
        System.out.println();
    }

    // Print the bottom border
    System.out.println("+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+");
    System.out.println();
}

public void viewParkingCharges() {
    System.out.println("Parking Charges:");
    System.out.println("Car: $10 per hour");
    System.out.println("Truck: $15 per hour");
    System.out.println("Bike: $5 per hour");
    System.out.println();
}


void displayAvailability() {
    // Find the maximum width of each column
    int[] columnWidths = new int[slots.get(0).size() + 1]; // +1 for the "Floor" column
    for (int floor = 0; floor < slots.size(); floor++) {
        for (int slotNum = 0; slotNum < slots.get(floor).size(); slotNum++) {
            Slot slot = slots.get(floor).get(slotNum);
            String vehicleType = (slot.vehicle != null) ? "[" + slot.vehicle.type.toUpperCase() + "]" : "[?????]";
            if (vehicleType.length() > columnWidths[slotNum + 1]) {
                columnWidths[slotNum + 1] = vehicleType.length();
            }
        }
    }

    System.out.println("+-----------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+");
    System.out.println("| Floor     | Slot 1  | Slot 2  | Slot 3  | Slot 4  | Slot 5  | Slot 6  | Slot 7  | Slot 8  | Slot 9  | Slot 10 |");
    System.out.println("+-----------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+");


    // Print each floor and its slots
    for (int floor = 0; floor < slots.size(); floor++) {
        System.out.print("+--------+");
        for (int i = 0; i < columnWidths.length; i++) {
            System.out.print(String.format("%-" + (columnWidths[i] + 2) + "s+", "").replace(' ', '-'));
        }
        System.out.println();
        System.out.printf("| Floor %-3d |", floor + 1);
        for (int slotNum = 0; slotNum < slots.get(floor).size(); slotNum++) {
            Slot slot = slots.get(floor).get(slotNum);
            String vehicleType = (slot.vehicle != null) ? "[" + slot.vehicle.type.toUpperCase() + "]" : "[?????]";
            System.out.printf(" %-"+columnWidths[slotNum + 1]+"s |", vehicleType);
        }
        System.out.println();
    }

    // Print the bottom border
     System.out.println("+-----------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+");
    System.out.println();
}




    public String parkVehicle(String type, String registration, int preferredFloor, int preferredSlot) {
        // Check if the preferred floor and slot are within the range of available floors and slots
        if (preferredFloor <= 0 || preferredFloor > slots.size() || preferredSlot <= 0 || preferredSlot > SLOTS_PER_FLOOR) {
            return "Invalid preferred floor or slot. Please choose within the available range.";
        }

        // Check if the preferred slot is available
        Slot preferredSlotObj = slots.get(preferredFloor - 1).get(preferredSlot - 1);
        if (preferredSlotObj.vehicle != null) {
            return "Preferred slot is not available. Please choose another slot.";
        }

        // Check if the preferred floor has available slots for the vehicle type
        if (type.equals("car") && availableCarSlots <= 0) {
            return "No available car slots on the preferred floor.";
        } else if (type.equals("truck") && availableTruckSlots <= 0) {
            return "No available truck slots on the preferred floor.";
        } else if (type.equals("bike") && availableBikeSlots <= 0) {
            return "No available bike slots on the preferred floor.";
        }

        // Park the vehicle at the preferred slot
        Slot slot = slots.get(preferredFloor - 1).get(preferredSlot - 1);
        Vehicle vehicle = new Vehicle(type, registration);
        slot.vehicle = vehicle;
        slot.ticketId = parkingLotId + "_" + preferredFloor + "_" + preferredSlot;
        slot.entryTime = LocalDateTime.now();

        updateAvailableSlots(type, -1);
        // Write to log file
        String logMessage = "[" + LocalDateTime.now().format(formatter) + "] Vehicle parked - Type: " + type + ", Registration: " + registration + ", Floor: " + preferredFloor + ", Slot: " + preferredSlot;
        writeToLogFile(logMessage);

        return slot.ticketId; // Return only the ticket ID
    }

    public boolean unparkVehicleByTicketId(String ticketId) {
        for (List<Slot> floor : slots) {
            for (Slot slot : floor) {
                if (ticketId.equals(slot.ticketId)) {
                    calculateAndDisplayCharges(slot);
                    slot.vehicle = null;
                    slot.ticketId = null;
                    slot.entryTime = null;
                    updateAvailableSlots(slot.type, 1);
                    // Write to log file
                    String logMessage = "[" + LocalDateTime.now().format(formatter) + "] Vehicle unparked - Ticket ID: " + ticketId;
                    writeToLogFile(logMessage);

                    return true;
                }
            }
        }
        return false;
    }

    public boolean unparkVehicleByRegistrationNumber(String registrationNumber) {
        for (List<Slot> floor : slots) {
            for (Slot slot : floor) {
                if (slot.vehicle != null && registrationNumber.equals(slot.vehicle.registration)) {
                    String ticketId = slot.ticketId; // Fix the ticketId reference
                    calculateAndDisplayCharges(slot);
                    slot.vehicle = null;
                    slot.ticketId = null;
                    slot.entryTime = null;
                    updateAvailableSlots(slot.type, 1);
                    // Write to log file
                    String logMessage = "[" + LocalDateTime.now().format(formatter) + "] Vehicle unparked - Ticket ID: " + ticketId;
                    writeToLogFile(logMessage);

                    return true;
                }
            }
        }
        return false;
    }

    public boolean unparkVehicle(String input, Scanner scanner) {
        // Check if the input is a ticket ID
        if (input.startsWith(parkingLotId)) {
            return unparkVehicleByTicketId(input);
        } else {
            // Input is a registration number, no need for scanner
            return unparkVehicleByRegistrationNumber(input);
        }
    }

    void unPark(String ticketId) {
        String[] extract = ticketId.split("_");
        int flr_idx = Integer.parseInt(extract[1]) - 1;
        int slot_idx = Integer.parseInt(extract[2]) - 1;

        Slot slot = slots.get(flr_idx).get(slot_idx);
        calculateAndDisplayCharges(slot);
        slot.vehicle = null;
        slot.ticketId = null;
        slot.entryTime = null;
        updateAvailableSlots(slot.type, 1);
        System.out.println("Unparked vehicle");
    }

    private String generateTicketId(List<Slot> floor, Slot slot) {
        int flr = slots.indexOf(floor) + 1;
        int slno = floor.indexOf(slot) + 1;
        return parkingLotId + "_" + flr + "_" + slno;
    }

    int getNoOfOpenSlots(String type) {
        switch (type) {
            case "car":
                return availableCarSlots;
            case "truck":
                return availableTruckSlots;
            case "bike":
                return availableBikeSlots;
            default:
                return 0;
        }
    }
    void updateAvailableSlots(String type, int change) {
        switch (type) {
            case "car":
                availableCarSlots += change;
                break;
            case "truck":
                availableTruckSlots += change;
                break;
            case "bike":
                availableBikeSlots += change;
                break;
        }
    }

    void displayOpenSlots(String type) {
        System.out.println("Available slots for " + type);
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.vehicle == null && slot.type.equals(type))
                    System.out.println("Floor " + (i + 1) + " slot " + (j + 1));
            }
        }
    }

    void calculateAndDisplayCharges(Slot slot) {
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(slot.entryTime, exitTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        double charges;
        if (slot.type.equals("car")) {
            charges = hours * 10 + (minutes > 0 ? 10 : 0); // $10 per hour for cars, charge for an additional hour if minutes > 0
        } else if (slot.type.equals("truck")) {
            charges = hours * 15 + (minutes > 0 ? 15 : 0); // $15 per hour for trucks, charge for an additional hour if minutes > 0
        } else {
            charges = hours * 5 + (minutes > 0 ? 5 : 0); // $5 per hour for bikes, charge for an additional hour if minutes > 0
        }

        System.out.println("Vehicle unparked successfully.");
        System.out.println("Parking duration: " + hours + " hours and " + minutes + " minutes.");
        System.out.println("Parking charges: $" + charges);
    }

    public Vehicle searchByRegistrationNumber(String regNo) {
        for (int i = 0; i < slots.size(); i++) {
            List<Slot> floor = slots.get(i);
            for (int j = 0; j < floor.size(); j++) {
                Slot slot = floor.get(j);
                if (slot.vehicle != null && slot.vehicle.registration.equals(regNo)) {
                    System.out.println();
                    System.out.println("Vehicle found:");
                    System.out.println("Type: " + slot.vehicle.type);
                    System.out.println("Registration number: " + slot.vehicle.registration);
                    System.out.println("Floor: " + (i + 1));
                    System.out.println("Slot: " + (j + 1));
                    return slot.vehicle;
                }
            }
        }
        System.out.println("Vehicle not found.");
        return null; // Vehicle not found
    }

    public Vehicle searchByTicketId(String ticketId) {
        for (int i = 0; i < slots.size(); i++) {
            List<Slot> floor = slots.get(i);
            for (int j = 0; j < floor.size(); j++) {
                Slot slot = floor.get(j);
                if (slot.ticketId != null && slot.ticketId.equals(ticketId)) {
                    System.out.println();
                    System.out.println("Vehicle found:");
                    System.out.println("Type: " + slot.vehicle.type);
                    System.out.println("Registration number: " + slot.vehicle.registration);
                    System.out.println("Floor: " + (i + 1));
                    System.out.println("Slot: " + (j + 1));
                    return slot.vehicle;
                }
            }
        }
        System.out.println("Vehicle not found.");
        return null; // Vehicle not found
    }
}
