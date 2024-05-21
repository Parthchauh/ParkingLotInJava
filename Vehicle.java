class Vehicle {
    String type;
    String registration;
    String id; // Add id property
    String licensePlate;

    public Vehicle(String type, String registration) {
        this.type = type;
        this.registration = registration;
    }

    // Getter methods
    public String getId() {
        return registration; // Assuming registration is the vehicle ID
    }

    public String getLicensePlate() {
        return registration; // Assuming registration is the license plate
    }
}

