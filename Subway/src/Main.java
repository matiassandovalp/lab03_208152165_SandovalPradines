import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Create Subway instance
        Subway subway = new Subway(1, "Main Subway");

        // Create Station instances
        Station station1 = new Station(1, 'r', "Station1", 5);
        Station station2 = new Station(2, Station.StationType.M, "Station2", 10);
        Station station4 = new Station(4, 'r', "Station4", 5);
        Station station5 = new Station(5, 'm', "Station5", 10);
        Station station6 = new Station(6, 'c', "Station6", 15);

        // Create some sample sections
        Section section1 = new Section(station1, station2, 100, 50);
        Section section2 = new Section(station2, station4, 150, 70);
        Section section3 = new Section(station4, station5, 200, 80);
        Section section4 = new Section(station5, station6, 250, 90);

        // Create Line objects
        Line line1 = new Line(1, "Line1", "Type1", List.of(section1, section2));
        Line line2 = new Line(2, "Line2", "Type2", List.of(section3, section4));

        // Add lines to subway
        subway.addLine(line1);
        subway.addLine(line2);

        // Calculate and print the total length and cost of the lines using the static method
        System.out.println("Total length of line1: " + line1.lineLength());
        System.out.println("Total length of line2: " + line2.lineLength());
        System.out.println("Total cost of line1: " + line1.lineCost());
        System.out.println("Total cost of line2: " + line2.lineCost());

        // Print the whole line details
        System.out.println("Details of line1: " + line1);
        System.out.println("Details of line2: " + line2);

        // Create and add a train to the subway
        Train train = new Train(1, "JAVA", 15, 12, null);
        subway.addTrain(train);

        // Create a Pcar and add it to the train
        Pcar.CarType carType = Pcar.CarType.CT;
        Pcar carro = new Pcar(122, 12, "JAVA", "JAVA", carType);

        try {
            train.addCar(carro, 0);  // Attempt to add carro at position 0
            System.out.println("Car added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add car: " + e.getMessage());
        }

        // Create Driver instances
        Driver driver1 = new Driver(1, "John Doe", "JAVA");
        Driver driver2 = new Driver(2, "Jane Smith", "JAVA");

        // Add drivers to subway
        subway.addDriver(driver1);
        subway.addDriver(driver2);

        // Print out the subway details at the end
        System.out.println(subway);
    }
}
