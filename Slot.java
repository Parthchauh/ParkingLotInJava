public class Slot {
    Vehicle vehicle;
    boolean isReserved;
    long parkedTime; // The time when the vehicle was parked, in milliseconds

    public Slot() {
        this.vehicle = null;
        this.isReserved = false;
        this.parkedTime = 0;
    }

    public boolean isAvailable() {
        return this.vehicle == null;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.parkedTime = System.currentTimeMillis();
    }

    public void unparkVehicle() {
        this.vehicle = null;
        this.parkedTime = 0;
    }

    public double calculateParkingCharge() {
    if (this.vehicle == null) {
        return 0;
    }

    long currentTime = System.currentTimeMillis();
    long durationInMillis = currentTime - this.parkedTime;
    long durationInHours = (durationInMillis / (1000 * 60 * 60)) + 1; // Round up to the next hour

    double ratePerHour = 10.0; // Example rate per hour
    return durationInHours * ratePerHour;
}    
    // Getter for isReserved
    public boolean isReserved() {
        return isReserved;
    }

    // Setter for isReserved
    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}

