public class Main {
    public static void main(String[] args) {
        // Create a Station instance with a character type
        Station station1 = new Station(1, 'r', "Station1", 5);
        System.out.println(station1);

        // Create a Station instance with StationType directly
        Station station2 = new Station(2, Station.StationType.M, "Station2", 10);
        System.out.println(station2);

        // Create a Station instance with an invalid character type to see the error handling
        try {
            Station station3 = new Station(3, 'x', "Station3", 15);
            System.out.println(station3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
