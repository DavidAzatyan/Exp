package YandexACA;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParkingLotApplication {
    private static final int TOTAL_PARKING_SPOTS = 200;
    private static int TOTAL_COMPACT_SPOTS = 100;
    private static final int TOTAL_LARGE_SPOTS = 100;
    private Map<VehicleType, ParkingSpot> parkingSpots;

    public ParkingLotApplication(HashMap<VehicleType, ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
        parkingSpots.put(VehicleType.CAR, new ParkingSpot(TOTAL_COMPACT_SPOTS));
        parkingSpots.put(VehicleType.TRUCK, new ParkingSpot(TOTAL_LARGE_SPOTS));
        parkingSpots.put(VehicleType.MOTORCYCLE, new ParkingSpot(TOTAL_COMPACT_SPOTS));
    }

    public void parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = parkingSpots.get(vehicle.getType());
        if (spot != null && spot.getAvailableSpots() > 0) {
            spot.decreaseAvailableSpots();
            System.out.println(vehicle.getType() + " with license plate " + vehicle.getLicensePlate() + " parked.");
        } else {
            System.out.println("No available spot for " + vehicle.getType() + " with license plate " + vehicle.getLicensePlate());
        }
    }

    public void retrieveVehicle(Vehicle vehicle) {
        ParkingSpot spot = parkingSpots.get(vehicle.getType());
        if (spot != null) {
            spot.increaseAvailableSpots();
            System.out.println(vehicle.getType() + " with license plate " + vehicle.getLicensePlate() + " retrieved.");
        } else {
            System.out.println("Vehicle not found in the parking lot.");
        }
    }

    public void displayAvailableSpots() {
        System.out.println("Available parking spots:");
        for (Map.Entry<VehicleType, ParkingSpot> entry : parkingSpots.entrySet()) {
            System.out.println(entry.getKey() + " spots: " + entry.getValue().getAvailableSpots());
        }
    }

    public static void main(String[] args) {
        HashMap<VehicleType, ParkingSpot> map = new HashMap<>();
        map.put(VehicleType.CAR, new ParkingSpot(TOTAL_COMPACT_SPOTS));
        map.put(VehicleType.TRUCK, new ParkingSpot(TOTAL_LARGE_SPOTS));
        map.put(VehicleType.MOTORCYCLE, new ParkingSpot(TOTAL_COMPACT_SPOTS));
        ParkingLotApplication parkingLot = new ParkingLotApplication(map);
        parkingLot.parkVehicle(new Vehicle("1233", VehicleType.CAR));
        parkingLot.parkVehicle(new Vehicle("46564", VehicleType.CAR));
        parkingLot.parkVehicle(new Vehicle("45gtr", VehicleType.MOTORCYCLE));
        parkingLot.parkVehicle(new Vehicle("46564", VehicleType.CAR));
        parkingLot.parkVehicle(new Vehicle("0221", VehicleType.TRUCK));
        parkingLot.parkVehicle(new Vehicle("dfwf2", VehicleType.TRUCK));
        parkingLot.parkVehicle(new Vehicle("t4hy5", VehicleType.MOTORCYCLE));
        parkingLot.parkVehicle(new Vehicle("erg545", VehicleType.MOTORCYCLE));
        parkingLot.displayAvailableSpots();

    }
}
