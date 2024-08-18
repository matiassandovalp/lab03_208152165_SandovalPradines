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
        System.out.println("Details of train: " + train);

        try {
            train.removeCar(0);  // Attempt to add carro at position 0
            System.out.println("Car removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to remove car: " + e.getMessage());
        }
        System.out.println("Details of train: " + train);

        // Create Driver instances
        Driver driver1 = new Driver(1, "John Doe", "JAVA");
        Driver driver2 = new Driver(2, "Jane Smith", "JAVA");

        // Add drivers to subway
        subway.addDriver(driver1);
        subway.addDriver(driver2);

        // Print out the subway details at the end
        System.out.println(subway);

        // Create some Pcars
        Pcar terminalCar1 = new Pcar(1, 100, "ModelA", "JAVA", Pcar.CarType.TR);
        Pcar terminalCar2 = new Pcar(2, 100, "ModelA", "JAVA", Pcar.CarType.TR);
        Pcar centralCar1 = new Pcar(3, 80, "ModelB", "JAVA", Pcar.CarType.CT);
        Pcar centralCar2 = new Pcar(4, 80, "ModelB", "JAVA", Pcar.CarType.CT);

        // Create an empty train
        Train trainTest = new Train(1, "JAVA", 60, 5, null);

        // Test adding cars
        try {
            trainTest.addCar(terminalCar1, 0); // Add terminal car at the beginning
            System.out.println("Added terminal car at the beginning: " + trainTest);
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }

        try {
            trainTest.addCar(terminalCar2, 1); // Add terminal car at the end
            System.out.println("Added terminal car at the end: " + trainTest);
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }

        try {
            trainTest.addCar(centralCar1, 1); // Add central car in the middle
            System.out.println("Added central car in the middle: " + trainTest);
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }



        // Test if it's a valid train
        System.out.println("Is valid train: " + trainTest.isTrain()); // Should be true

        // Test removing a car
        try {
            trainTest.removeCar(1); // Remove the central car
            System.out.println("Train after removing a car: " + trainTest);
        } catch (IllegalArgumentException e) {
            System.out.println("Error removing car: " + e.getMessage());
        }

        // Test if it's a valid train after removal
        System.out.println("Is valid train after removal: " + trainTest.isTrain()); // Should be false

        // Add central car again in the middle
        try {
            trainTest.addCar(centralCar2, 1);
            System.out.println("Train after re-adding a central car: " + trainTest);
        } catch (IllegalArgumentException e) {
            System.out.println("Error re-adding car: " + e.getMessage());
        }

        // Test if it's a valid train after re-adding
        System.out.println("Is valid train after re-adding: " + trainTest.isTrain()); // Should be true
        System.out.println("\n\n\n");
        Menu menu = new Menu();
        menu.show();

    }
}
