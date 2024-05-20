# Parking Lot Management System

The Parking Lot Management System is a Java-based microproject designed to efficiently manage parking lot operations. This system provides users with the ability to park and unpark vehicles while maintaining track of available slots and offering a user-friendly interface for interactions. Its primary goal is to streamline the process of managing parking spaces, ensuring an organized and convenient experience for both operators and users of the parking lot.

## Key Features

1. **Park a Vehicle**: Users can park their vehicles by specifying the type (car, truck, or bike), registration number, and their preferred floor and slot. The system checks availability and confirms parking or suggests alternatives if the preferred slot is occupied.

2. **Unpark a Vehicle**: Users can unpark their vehicles using either a ticket ID or registration number. The system calculates parking duration and charges, if applicable, and updates slot availability.

3. **View Parked Vehicles**: Operators can view a list of all parked vehicles, including type, registration number, and location within the parking lot.

4. **Display Parking Availability**: Users can check current slot availability across different floors, aiding in decision-making for vehicle parking.

5. **Search Vehicles**: The system allows searching for vehicles by ticket ID or registration number, facilitating the location of parked vehicles.

6. **User-Friendly Interface**: The command-line interface is intuitive and easy to use, guiding users through various functionalities with clear prompts and messages.

## Technical Details

- **Programming Language**: Java
- **Data Structures**: Lists and custom classes are utilized to manage parking slots, vehicles, and their associations.
- **Time Management**: Entry time of parked vehicles is recorded to accurately calculate parking duration.

## Usage

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/parking-lot.git
2. Output of this repo:
   Choose an option:
    1. View parked vehicles
    2. Park a vehicle
    3. Display parking availability
    4. Unpark a vehicle
    5. Exit
