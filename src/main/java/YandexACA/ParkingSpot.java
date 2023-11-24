package YandexACA;

public class ParkingSpot {
    private int capacity;
    private int availableSpots;

    public ParkingSpot(int capacity) {
        this.capacity = capacity;
        this.availableSpots = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void decreaseAvailableSpots() {
        if (availableSpots > 0) {
            availableSpots--;
        }
    }

    public void increaseAvailableSpots() {
        if (availableSpots < capacity) {
            availableSpots++;
        }
    }
}
