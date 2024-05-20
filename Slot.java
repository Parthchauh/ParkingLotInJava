import java.time.LocalDateTime;

class Slot {
    String type;
    Vehicle vehicle;
    String ticketId;
    LocalDateTime entryTime;

    Slot(String type) {
        this.type = type;
        this.vehicle = null;
        this.ticketId = null;
        this.entryTime = null;
    }
}
